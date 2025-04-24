package org.copy.redis.server.persistence.Rdb.Serializer;

import org.copy.redis.server.DataStructure.RedisObject;
import org.copy.redis.server.DataStructure.SDS;
import org.copy.redis.server.interfaces.RDBSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class StringSerializer implements RDBSerializer {
    private static final Logger logger = LoggerFactory.getLogger(StringSerializer.class);
    @Override
    public void Serializer( RedisObject redisObj, DataOutputStream dos) {
        // value
        byte[] valueBytes = ((SDS) redisObj.getPtr()).getBuilder().toString().getBytes(StandardCharsets.UTF_8);
        try {
            dos.writeInt(valueBytes.length);
            dos.write(valueBytes);
        } catch (IOException e) {
            logger.error("stringSerializer出错{}",e.getMessage());
        }
    }
}
