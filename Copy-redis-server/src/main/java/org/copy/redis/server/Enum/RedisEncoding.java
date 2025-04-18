package org.copy.redis.server.Enum;

import lombok.Getter;

@Getter
public enum RedisEncoding {
    REDIS_ENCODING_INT((byte) 1),REDIS_ENCODING_EMBSTR((byte) 2),REDIS_ENCODING_RAW((byte) 3),
    REDIS_ENCODING_HT((byte) 4),REDIS_ENCODING_LINKEDLIST((byte) 15),
    REDIS_ENCODING_ZIPLIST((byte) 6),REDIS_ENCODING_INTSET((byte) 7),
    REDIS_ENCODING_SKIPLIST((byte) 8);
    byte type;
    RedisEncoding(byte type) {
        this.type = type;
    }
}
