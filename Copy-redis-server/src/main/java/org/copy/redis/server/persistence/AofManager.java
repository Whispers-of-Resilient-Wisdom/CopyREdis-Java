package org.copy.redis.server.persistence;

import lombok.SneakyThrows;
import org.copy.redis.server.Correspond.CommandProcessor;
import org.copy.redis.server.Enity.Command;
import org.copy.redis.server.Enity.ReceiveCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
//单一实例
public class AofManager {
    private  BufferedWriter writer;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private static final Logger logger = LoggerFactory.getLogger(AofManager.class);
    private static final String AOF_DIR = "aof";
    private static final String AOF_FILENAME = "appendonly.aof";

    private AofManager()  {
        // 确保目录存在
        try {
            Files.createDirectories(Paths.get(AOF_DIR));
        } catch (IOException e) {
            logger.error(" 目录不存在失败{}",e.getMessage());
        }

        Path aofPath = Paths.get(AOF_DIR, AOF_FILENAME);

        // 初始化文件
        this.writer=null;
        if (!Files.exists(aofPath)) {
            try {
                Files.createFile(aofPath);
            } catch (IOException e) {
                logger.error(" 创建文件失败{}",e.getMessage());
            }
            logger.info("Created new AOF file: {}", aofPath);
        } else {
            logger.info("Using existing AOF file: {}", aofPath);
        }

        // 初始化写入器 (追加模式)
        try {
            this.writer = Files.newBufferedWriter(aofPath,
                    StandardCharsets.UTF_8,
                    StandardOpenOption.APPEND,
                    StandardOpenOption.CREATE);
        } catch (IOException e) {
          logger.error(" 追加创建失败{}",e.getMessage());
        }
    }

    public void append(String respCommand) {
        executor.submit(() -> {
            try {
                writer.write(respCommand);
                writer.flush(); // 你也可以使用 fsync 控制
            } catch (IOException e) {
                logger.error(" 添加出错{}", e.getMessage());
            }
        });
    }

    @SneakyThrows
    public void loadAof() {
        int expectedArgCount = 0;
        Path aofPath = Paths.get(AOF_DIR, AOF_FILENAME);

        int totalBytes =0;
        ReceiveCommand receiveCommand = new ReceiveCommand();
        StringBuilder respCommand = null;

        Command command=new Command(null,null,null);
        try (BufferedReader reader = Files.newBufferedReader(aofPath, StandardCharsets.UTF_8)) {
            String line = "12";
            List<String> buffer = new ArrayList<>();

            while (true) {
                try {
                    if ((line = reader.readLine()) == null) break;
                } catch (IOException e) {
                    logger.error("{}", e.getMessage());
                }
                if(respCommand == null) {
                    respCommand = new StringBuilder();
                }
                if (line.startsWith("*")) {
                    expectedArgCount = Integer.parseInt(line.substring(1));
                    totalBytes += line.getBytes(StandardCharsets.UTF_8).length + 2;
                    respCommand = receiveCommand.getRespCommand();
                    respCommand.setLength(0);
                    respCommand.append(line).append("\r\n");  //  添加 \r\n
                    buffer.clear();
                } else if (line.startsWith("$")) {

                    respCommand.append(line).append("\r\n");
                    totalBytes += line.getBytes(StandardCharsets.UTF_8).length + 2;
                } else {
                    buffer.add(line);
                    respCommand.append(line).append("\r\n");  //  添加 \r\n
                    totalBytes += line.getBytes(StandardCharsets.UTF_8).length + 2;
                    if (buffer.size() == expectedArgCount) {
                        receiveCommand.setCommand(buffer.toArray(new String[0]));
                        CommandProcessor.submit(command);
                        buffer.clear();
                        expectedArgCount = -1;
                        totalBytes = 0;
                    }
                }
            }

            //shutdown();或许还需要append
        }
    }

   private static class AofManagerHolder {//单一实例
    // 唯一实例
    private static final AofManager INSTANCE = new AofManager();
   }
    public static AofManager getInstance() {
        return AofManagerHolder.INSTANCE;
    }

    public void shutdown()  {
        try {
            writer.close();
        } catch (IOException e) {
            logger.error(" 关闭失败{}",e.getMessage());
        }
        executor.shutdown();
    }

    }

