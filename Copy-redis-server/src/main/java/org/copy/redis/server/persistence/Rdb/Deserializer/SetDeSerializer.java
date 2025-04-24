package org.copy.redis.server.persistence.Rdb.Deserializer;

import org.copy.redis.server.DataStructure.RedisObject;
import org.copy.redis.server.DataStructure.SDS;
import org.copy.redis.server.Enum.RedisEncoding;
import org.copy.redis.server.Enum.RedisType;
import org.copy.redis.server.interfaces.RdbDeserializer;

import java.io.DataInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class SetDeSerializer implements RdbDeserializer {

    @Override
    public RedisObject deserialize(DataInputStream dis) throws IOException {

    }
}