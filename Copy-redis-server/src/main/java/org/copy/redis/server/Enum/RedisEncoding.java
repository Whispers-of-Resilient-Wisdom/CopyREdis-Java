package org.copy.redis.server.Enum;

public enum RedisEncoding {
    REDIS_ENCODING_INT(1),REDIS_ENCODING_EMBSTR(2),REDIS_ENCODING_RAW(3),
    REDIS_ENCODING_HT(4),REDIS_ENCODING_LINKEDLIST(5),
    REDIS_ENCODING_ZIPLIST(6),REDIS_ENCODING_INTSET(7),
    REDIS_ENCODING_SKIPLIST(8);
    int type;
    RedisEncoding(int type) {
        this.type = type;
    }
}
