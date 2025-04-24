package org.copy.redis.server.interfaces;

import org.copy.redis.server.DataStructure.RedisObject;

import java.io.DataInputStream;
import java.io.IOException;

public interface RdbDeserializer {
    RedisObject deserialize(DataInputStream dis) throws IOException;
}

