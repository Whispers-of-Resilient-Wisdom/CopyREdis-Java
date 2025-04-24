package org.copy.redis.server.Correspond;

import org.copy.redis.server.Enity.Command;
import org.copy.redis.server.Enity.ReplicationBacklog;
import org.copy.redis.server.persistence.AofManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import static org.copy.redis.server.CommandHandler.CommandContext.executeCommand;
import static org.copy.redis.server.Enity.ReplicationBacklog.getInstance;
import static org.copy.redis.server.Enity.enums.WriterCommand.isWriter;

public class CommandProcessor {
    private static final BlockingQueue<Command> commandQueue = new LinkedBlockingQueue<>();
    private static final Logger logger = LoggerFactory.getLogger(CommandProcessor.class);
    private static volatile Thread processorThread;

    static {
        startProcessorThread();
    }

    private static void startProcessorThread() {
        processorThread = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    Command cmd = commandQueue.take();
                    logger.debug("Processing command by {}: {}",
                            Thread.currentThread().getName(), cmd.getRawCommand());
                    handle(cmd);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    logger.info("Processor thread interrupted");
                    break;
                } catch (Exception e) {
                    logger.error("Command handling failed", e);
                }
            }
        }, "Redis-Command-Processor");
        processorThread.setDaemon(true); // 设为守护线程（可选）
        processorThread.start();
    }

    public static void submit(Command command) {
        if (!commandQueue.offer(command)) {
            logger.warn("Command queue full, dropping: {}", (Object[]) command.getRawCommand());
            sendErrorResponse(command, "-ERR server busy\r\n");
        }
    }
    //判断类型可以在每个handler内部处理，不需要单线程
    // --在这里实现aof,缓存加载区，RDB，惰性删除与主动删除所进行的DEL命令
    // 主机也要告诉从机
    private static void handle(Command command) {
        String[] parts = command.getRawCommand();
        if (parts == null || parts.length == 0||command.getRespCommand()==null) {
            sendErrorResponse(command, "-ERR empty command\r\n");
            return;
        }

        String response = executeCommand(parts);
        if (command.getCtx()==null ) {
            //正常来说应该是在这里添加后，直接
            return ;  //这是由于ctx我设置为null为正在加载状态
        }
        //todo aof ，缓存加载区冲
            if(isWriter(parts[0])){//添加到缓区 --添加到aof中
            ReplicationBacklog instance = getInstance();
            instance.append(command.getRespCommand().getBytes());
            AofManager aofInstance = AofManager.getInstance();
            aofInstance.append(command.getRespCommand());

            }

        command.getCtx().writeAndFlush(response);
    }

    private static void sendErrorResponse(Command cmd, String error) {
        if (cmd != null && cmd.getCtx() != null) {
            cmd.getCtx().writeAndFlush(error);
        }
    }

    public static void shutdown() {
        if (processorThread != null) {
            processorThread.interrupt();
        }
    }
}