package org.copy.redis.client.interfaces;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public interface CommandInterface< V> {
    String build(String command,String... args); // 构造 Redis 协议格式
    //set key value
    public void set(String key, V value) throws IOException ;
    //get key value
    public V get(String key) throws IOException ;
    public void close() throws IOException ;
    //del key
    public void del(String key) throws IOException ;
    //expire key seconds
    public void expire(String key, long seconds) throws IOException ;
    //EXISTS key
    public boolean exists(String key) throws IOException ;
    //TTL key
    public void TTL(String key) throws IOException ;
    //PERSIST 移除过期时间
    public void PERSIST(String key) throws IOException;
    //keys *
    public int KEYS(String key) throws IOException ;
    //rename oldKey new Key
    public void RENAME(String  oldKey,String newKey) throws IOException ;
    //incr key
    public void  INCR(String key) throws IOException ;
    //append key value
    public void APPEND(String key,String value) throws IOException ;
    //decr key
    public void    DECR (String key) throws IOException ;
    //todo List --Hash
    public void    MSET (HashMap<String, V> value) throws IOException ;//设置序列化
    //STRLEN key ( 求长度 length)
    public void STRLEN(String key) throws IOException ;
    //todo LPUSH key value1 value2 ...
    public void LPUSH(String key, List<V> value) throws IOException ;
    //RPUSH key value1 value2 ...
    public void RPUSH(String key, List<String > value) throws IOException ;
    //LPOP key
    public V LPOP(String key) throws IOException;
    //RPOP key 移除并返回列表尾部的元素
    public void RPOP(String key) throws IOException;

    //LRANGE key start stop --序列化
    public List<V> LRANGE(String key,int start ,int stop) throws IOException ;
    //LLEN key	获取列表长度
    public int  LLEN(String key) throws IOException ;
    //LREM key count value	删除列表中指定的值
    public boolean  LREM(String key,int count,int value) throws IOException;
    //LINDEX key index	获取列表中指定索引的元素
    public boolean INDEX(String key,int index) throws IOException ;
    //  todo  SADD key member1 member2 ...	向集合添加元素
    public boolean INDEX(String key,List<String> member) throws IOException ;
    //    SREM key member	删除集合中的元素

    public void SREM(String key,String member) throws IOException ;
    //    SMEMBERS key	获取集合中的所有元素
    public void SMEMBERS(String key) throws IOException ;



    //    SISMEMBER key member	判断元素是否存在于集合中
    public void SISMEMBER(String key,String member) ;
    // SCARD key 查看集合大小
    public void SCARD(String key) throws IOException;
    //    SRANDMEMBER key [count]	随机返回集合中的一个或多个元素
    public void SRANDMEMBER(String key,int count) throws IOException ;
    //    SDIFF key1 key2	返回 key1 与 key2 的差集
    public void SDIFF(String key1,String key2) throws IOException ;
    //    SINTER key1 key2	返回 key1 与 key2 的交集
    public void SINTER(String key1,String key2) throws IOException ;
    //    SUNION key1 key2	返回 key1 与 key2 的并集
    public void SUNION(String key1,String key2) throws IOException ;
//    ZADD key score1 member1 score2 member2 ...	添加元素到有序集合并设置分数
//    ZREM key member	删除有序集合中的元素
//    ZCARD key	获取有序集合中的元素数量
//    ZRANGE key start stop [WITHSCORES]	按分数从低到高获取元素
//    ZREVRANGE key start stop [WITHSCORES]	按分数从高到低获取元素
//    ZSCORE key member	获取元素的分数
//    ZRANK key member	获取元素的排名（从 0 开始）
//    ZREVRANK key member	获取元素的倒序排名
//    ZINCRBY key increment member	增加某个元素的分数

}
