package org.copy.redis.server.persistence.Rdb.Serializer;

import org.copy.redis.server.DataStructure.RedisDict;
import org.copy.redis.server.DataStructure.RedisObject;
import org.copy.redis.server.interfaces.RDBSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class HashSerializer implements RDBSerializer {
    private static final Logger logger = LoggerFactory.getLogger(HashSerializer.class);
    @Override
    public void Serializer(RedisObject redisObj, DataOutputStream dos) {
        // value
        Map<String, String> dict = ((RedisDict) (redisObj.getPtr())).getDict();

        try {
            dos.writeInt(dict.size());
            for (Map.Entry<String, String> entry : dict.entrySet()) {
                byte[] fieldBytes = entry.getKey().getBytes(StandardCharsets.UTF_8);
                byte[] valueBytes = entry.getValue().getBytes(StandardCharsets.UTF_8);
                dos.writeInt(fieldBytes.length);
                dos.write(fieldBytes);
                dos.writeInt(valueBytes.length);
                dos.write(valueBytes);


            }
        } catch (IOException e) {
            logger.error("stringSerializer出错{}",e.getMessage());
        }
    }
}