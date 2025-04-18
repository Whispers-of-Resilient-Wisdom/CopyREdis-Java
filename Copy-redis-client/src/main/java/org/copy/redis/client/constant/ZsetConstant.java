package org.copy.redis.client.constant;

import lombok.Getter;

/**
 * ZRANGE,ZREVRANGE,ZSCORE,ZRANK 读操作
 * ZADD，ZREM，ZINCRBY，ZREMRANGEBYRANK 写操作

 * ZRANGE key start stop [WITHSCORES]	按分值升序获取	O(log(N)+M)
 * ZREVRANGE key start stop [WITHSCORES]	按分值降序获取	O(log(N)+M)
 * ZSCORE key member	获取元素分值	O(1)
 * ZRANK key member	获取元素升序排名	O(log(N))

 * 写操作

 * `ZADD key [NX	XX] [CH] [INCR] score member`	添加/更新元素	O(log(N))
 * ZREM key member [member...]	删除元素	O(M*log(N))
 * ZINCRBY key increment member	增加元素分值	O(log(N))
 * ZREMRANGEBYRANK key start stop	按排名范围删除	O(log(N)+M)
 */
@Getter
public enum ZsetConstant {
    ZRANGE("ZRANGE"),ZREVRANGE("ZREVRANGE"),ZSCORE("ZSCORE"),ZRANK("ZRANK")
    ,ZADD("ZADD"),ZREM("ZREM"),ZINCRBY("ZINCRBY"),ZREMRANGEBYRANK("ZREMRANGEBYRANK");
    private String constant;

    ZsetConstant(String ttl) {
        constant = ttl;
    }
}
