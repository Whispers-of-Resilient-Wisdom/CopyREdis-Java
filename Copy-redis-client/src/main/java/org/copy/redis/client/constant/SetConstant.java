package org.copy.redis.client.constant;

import lombok.Getter;

/**
 SMEMBERS,SISMEMBER,SCARD,SRANDMEMBER读操作
 SADD,SREM,SPOP,SINTERSTORE;//写操作
 可能还要加点交集，并集，差集
 * SMEMBERS key	获取所有元素	O(N)
 * SISMEMBER key member	检查元素是否存在	O(1)
 * SCARD key	获取元素数量	O(1)
 * SRANDMEMBER key [count]	随机获取元素	O(N)
 * 写操作

 * SADD key member [member...]	添加元素	O(1)每个
 * SREM key member [member...]	删除元素	O(N)
 * SPOP key [count]	随机删除元素	O(1)
 * SINTERSTORE destination key [key...]	求交集并存储	O(N*M)
 */
@Getter
public enum SetConstant {
    SMEMBERS("SMEMBERS"),SISMEMBER("SISMEMBER"),SCARD("SCARD")
    ,SRANDMEMBER("SRANDMEMBER"),SADD("SADD"),SREM("SREM"),SIN("SIN"),;
    private String constant;

    SetConstant(String ttl) {
        constant = ttl;
    }
}
