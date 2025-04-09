package org.copy.redis.server.Enum;

public enum RedisType {
    REDIS_STRING(1),REDIS_LIST(2),REDIS_HASH(3),REDIS_SET(4),REDIS_ZSET(5);
    int type;
    RedisType(int type) {
    this.type = type;
    }
}
