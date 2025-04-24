package org.copy.redis.server.persistence.Rdb;

import org.copy.redis.server.DataStructure.*;
import org.copy.redis.server.interfaces.RdbDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Map;

import static org.copy.redis.server.persistence.Rdb.Serializer.RDBSerializerContext.SerializerHandler;


//实现rdb的功能
public class RdbManager extends RedisServerDS {
    private static final Logger logger = LoggerFactory.getLogger(RdbManager.class);
    private static final String RDB_DIR = "RDB";
    private static final String RDB_FILENAME = "dump.rdb";

    public void saveRDBToFile( ) throws IOException {
        Path RdbPath = Paths.get(RDB_DIR, RDB_FILENAME);
        try (FileOutputStream fos = new FileOutputStream(RdbPath.toFile());
             DataOutputStream dos = new DataOutputStream(fos)) {

            // 1. 写入 RDB 文件头部（包括版本号等）
            dos.write(new byte[]{0x52, 0x44, 0x42, 0x31}); // RDB 文件标识符 "RDB1"
            dos.writeInt(2); // 当前版本号 (比如是版本 2)

            // 2. 遍历所有键值对，写入数据
            for (Map.Entry<String, RedisObject> entry : map.entrySet()) {
                SerializerHandler(entry.getKey(), entry.getValue(), dos);
            }

            // 3. 写入 RDB 文件结束标记
            dos.writeByte(0xFF); // 文件结束标志
            dos.flush();
        }
    }
    public void loadRDBFromFile() throws IOException {
        Path rdbPath = Paths.get(RDB_DIR, RDB_FILENAME);

        try (FileInputStream fis = new FileInputStream(rdbPath.toFile());
             DataInputStream dis = new DataInputStream(fis)) {

            // 1. 校验头部
            byte[] magic = new byte[4];
            dis.readFully(magic);
            if (!Arrays.equals(magic, new byte[]{0x52, 0x44, 0x42, 0x31})) {
                throw new IOException("非法的 RDB 文件标头");
            }

            int version = dis.readInt();
            if (version != 2) {
                throw new IOException("不支持的 RDB 版本: " + version);
            }

            // 2. 循环读取数据，直到遇到结束标志
            while (true) {
                int type = dis.readUnsignedByte();
                if (type == 0xFF) {
                    break; // 结束标志
                }

                // 读取 key
                int keyLen = dis.readInt();
                byte[] keyBytes = new byte[keyLen];
                dis.readFully(keyBytes);
                String key = new String(keyBytes, StandardCharsets.UTF_8);

                // 反序列化 value
                RedisDataType redisType = RedisDataType.fromCode(type);
                RdbDeserializer deserializer = DESERIALIZER_REGISTRY.get(redisType);
                if (deserializer == null) {
                    throw new IOException("未知类型：" + type);
                }

                RedisObject value = deserializer.deserialize(dis);
                map.put(key, value);
            }
        }
    }







}
