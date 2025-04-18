package org.copy.redis.client.constant;

import lombok.Getter;

/**
 *
 GET,MGET,STRLEN; 读操作

 SET,SETNX,INCR,DECR,INCRBY,DECR,APPEND;写操作
 */
@Getter
public enum StringConstant {
    GET("GET"),MGET("MGET"),SET("SET"),MSET("MSET"),
    STRLEN("STRLEN"),APPEND("APPEND"),INCR("INCR"),DECR("DECR"),
    INCRBY("INCRBY"),DECRBY("DECRBY");
    private String constant;

    StringConstant(String ttl) {
        constant = ttl;
    }
}
