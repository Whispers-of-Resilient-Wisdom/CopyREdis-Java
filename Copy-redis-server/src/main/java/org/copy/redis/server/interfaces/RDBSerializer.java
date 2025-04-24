package org.copy.redis.server.interfaces;

import org.copy.redis.server.DataStructure.RedisObject;

import java.io.DataOutputStream;

public interface RDBSerializer {
    void Serializer(RedisObject redisObj, DataOutputStream dos);//进行序列化
}
