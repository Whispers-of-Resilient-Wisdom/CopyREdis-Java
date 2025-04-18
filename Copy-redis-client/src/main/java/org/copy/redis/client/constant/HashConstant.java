package org.copy.redis.client.constant;

import lombok.Getter;

/**
 *
 *HGET,HMGET,HVALS,HLEN,HMGETALL 读操作
 * HSET,HMSET,HDEL,HDELALL,HINCRBY，HINCRBYFLOAT 写操作

 HGETALL key	获取所有字段和值	O(N)
 HVALS key	获取所有字段值	O(N)
 HLEN key	获取字段数量	O(1)
 HINCRBY key field increment	字段值增加	O(1)
 HINCRBYFLOAT key field increment	字段值浮点增加	O(1)
 */
@Getter
public enum HashConstant {
    HGET("HGET"),HMGET("HMGET"),HGETALL("HGETALL"),HSET("HSET"),HMSET("HMSET"),
    HDEL("HDEL"),HDELALL("HDELALL"),HVALS("HVALS"),HLEN("HLEN"),
    HINCRBYFLOAT("HINCRBYFLOAT"),HINCRBY("HINCRBY");
    private String constant;

    HashConstant(String ttl) {
        constant = ttl;
    }
}
