package org.copy.redis.client.constant;

import lombok.Getter;

/**
 *
 *  LRANGE,LINDEX,LLEN,读操作
 *  LPUSH,RPUSH,LPOP,RPOP,LREM,LTRIM 写操作

 * LRANGE key start stop	获取范围元素	O(S+N)
 * LINDEX key index	获取指定位置元素	O(N)
 * LLEN key	获取列表长度	O(1)
 * 写操作
 * LPUSH key value [value...]	左端插入元素	O(1)每个
 * RPUSH key value [value...]	右端插入元素	O(1)每个
 * LPOP key	左端弹出元素	O(1)
 * RPOP key	右端弹出元素	O(1)
 * LREM key count value	删除元素	O(N)
 * LTRIM key start stop	裁剪列表	O(N)
 */
@Getter
public enum ListConstant {
    LRANGE("LRANGE"),LINDEX("LINDEX"),LLEN("LLEN"),
    LPUSH("LPUSH"),RPUSH("RPUSH"),LPOP("LPOP"),
    RPOP("RPOP"),LREM("LREM"),LTRIM("LTRIM"),;
    private String constant;

    ListConstant(String ttl) {
        constant = ttl;
    }
}
