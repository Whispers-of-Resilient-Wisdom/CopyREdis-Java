package org.copy.redis.server.persistence.Rdb.Deserializer;

import org.copy.redis.server.DataStructure.RedisObject;
import org.copy.redis.server.DataStructure.SDS;
import org.copy.redis.server.Enum.RedisEncoding;
import org.copy.redis.server.Enum.RedisType;
import org.copy.redis.server.interfaces.RdbDeserializer;

import java.io.DataInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class StringDeSerializer implements RdbDeserializer {

    @Override
    public RedisObject deserialize(DataInputStream dis) throws IOException {
        // 反序列化 String 类型的 RedisObject
        int valueLength = dis.readInt();
        byte[] valueBytes = new byte[valueLength];
        dis.readFully(valueBytes);
        SDS sds = new SDS(new String(valueBytes, StandardCharsets.UTF_8));
        return new RedisObject(RedisType.REDIS_STRING.getType(), RedisEncoding.REDIS_ENCODING_EMBSTR.getType(),
                sds);
    }
}
