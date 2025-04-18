package org.copy.redis.server.Enum;

import lombok.Getter;

@Getter
public enum RedisType {
    REDIS_STRING((byte) 1),REDIS_LIST((byte) 2),REDIS_HASH((byte) 3),REDIS_SET((byte) 4),REDIS_ZSET((byte) 5);
    byte type;
    RedisType(byte type) {
    this.type = type;
    }
}
