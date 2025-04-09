package org.copy.redis.server.DataStructure;

public class redisObject {
    byte type;
    long encoding;
    Object ptr;//底层实现的数据结构
}
