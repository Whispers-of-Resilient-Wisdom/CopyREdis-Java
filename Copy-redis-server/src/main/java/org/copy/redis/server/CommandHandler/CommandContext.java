package org.copy.redis.server.CommandHandler;

import org.copy.redis.server.CommandHandler.Common.*;
import org.copy.redis.server.CommandHandler.Hash.*;
import org.copy.redis.server.CommandHandler.String.*;
import org.copy.redis.server.interfaces.CommandStrategy;

import java.util.*;
/*
  包含TTL,TYPE,EXISTS --读操作
  DEL,RENAME，EXPIRE --写操作
 */
/*

 HGET,HMGET,HVALS,HLEN,HMGETALL 读操作
  HSET,HMSET,HDEL,HDELALL,HINCRBY，HINCRBYFLOAT 写操作**/
/*

   LRANGE,LINDEX,LLEN,读操作
   LPUSH,RPUSH,LPOP,RPOP,LREM,LTRIM 写操作

  LRANGE key start stop	获取范围元素	O(S+N)
  LINDEX key index	获取指定位置元素	O(N)
  LLEN key	获取列表长度	O(1)
  写操作
  LPUSH key value [value...]	左端插入元素	O(1)每个
  RPUSH key value [value...]	右端插入元素	O(1)每个
  LPOP key	左端弹出元素	O(1)
  RPOP key	右端弹出元素	O(1)
  LREM key count value	删除元素	O(N)
  LTRIM key start stop	裁剪列表	O(N)
 */
/*
 SMEMBERS,SISMEMBER,SCARD,SRANDMEMBER读操作
 SADD,SREM,SPOP,SINTERSTORE;//写操作
 可能还要加点交集，并集，差集**/
/*

 GET,MGET,STRLEN; 读操作

 SET,SETNX,INCR,DECR,INCRBY,DECR,APPEND;写操作
 */

/**
 * ZRANGE,ZREVRANGE,ZSCORE,ZRANK 读操作
 * ZADD，ZREM，ZINCRBY，ZREMRANGEBYRANK 写操作**/
public class CommandContext {
      protected final static Map<String, CommandStrategy> strategyMap = new HashMap<>() ;
      static {
      //String
      strategyMap.put(constant.GET.getConstant(), new GetCommandStrategy());
      strategyMap.put(constant.SET.getConstant(), new SetCommandStrategy());
      strategyMap.put(constant.MGET.getConstant(), new MgetCommandStrategy());
      strategyMap.put(constant.MSET.getConstant(), new MSetCommandStrategy());
      strategyMap.put(constant.INCRBY.getConstant(), new IncreByCommandStrategy());
      strategyMap.put(constant.SETNX.getConstant(), new SetNxCommandStrategy());
      //common --5
      strategyMap.put(constant.TTL.getConstant(), new TtlCommandStrategy());
      strategyMap.put(constant.TYPE.getConstant(), new TypeCommandStrategy());
      strategyMap.put(constant.EXISTS.getConstant(), new ExistsCommandStrategy());
      strategyMap.put(constant.EXPIRE.getConstant(), new ExpireCommandStrategy());
      strategyMap.put(constant.DEL.getConstant(), new DelCommandStrategy());
      strategyMap.put(constant.FLUSHALL.getConstant(), new FlushAllCommandStrategy());//清除库


      //Hash --10
      strategyMap.put(constant.HDEL.getConstant(), new HdelCommandStrategy());
      strategyMap.put(constant.HGET.getConstant(), new HGetCommandStrategy());
      strategyMap.put(constant.HGETALL.getConstant(), new HGetAllCommandStrategy());
      strategyMap.put(constant.HLEN.getConstant(), new HLenCommandStrategy());
      strategyMap.put(constant.HMGET.getConstant(), new HMGetCommandStrategy());
      strategyMap.put(constant.HINCRBY.getConstant(), new HincrbyCommandStrategy());
      strategyMap.put(constant.HSET.getConstant(), new HSetCommandStrategy());
      strategyMap.put(constant.HVALS.getConstant(), new HvalsCommandStrategy());
      strategyMap.put(constant.HKEYS.getConstant(), new HKeysCommandStrategy());
      strategyMap.put(constant.HEXISTS.getConstant(), new HExistsCommandStrategy());




}

        public static String executeCommand(String[] args) {
            String cmd = args[0].toUpperCase();
            CommandStrategy strategy = strategyMap.get(cmd);

            if (strategy == null) {
                return "-ERR unknown command '" + cmd + "'\r\n";
            }



            return strategy.execute(args);
        }
    }


