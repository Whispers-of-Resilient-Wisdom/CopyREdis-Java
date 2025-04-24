package org.copy.redis.server.CommandHandler;

import org.copy.redis.server.DataStructure.RedisObject;
import org.copy.redis.server.DataStructure.RedisServerDS;
import org.copy.redis.server.DataStructure.RedisZset;
import org.copy.redis.server.Encoder.RespEncoder;
import org.copy.redis.server.Enum.RedisEncoding;
import org.copy.redis.server.Enum.RedisType;

import java.util.*;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * ZRANGE,ZREVRANGE,ZSCORE,ZRANK 读操作
 * ZADD，ZREM，ZINCRBY，ZREMRANGEBYRANK 写操作

 * ZRANGE key start stop [WITHSCORES]	按分值升序获取	O(log(N)+M) 成功
 * ZREVRANGE key start stop [WITHSCORES]	按分值降序获取	O(log(N)+M)
 * ZSCORE key member	获取元素分值	O(1) 成功
 * ZRANK key member	获取元素升序排名	O(log(N)) 成功

 * 写操作

 * `ZADD key [NX	XX] [CH] [INCR] score member`	添加/更新元素	O(log(N)) 成功
 * ZREM key member [member...]	删除元素	O(M*log(N)) 成功
 * ZINCRBY key increment member	增加元素分值	O(log(N))
 * ZREMRANGEBYRANK key start stop	按排名范围删除	O(log(N)+M)
 */
public  class ZSetCommand extends RedisServerDS  {
    // 添加元素 //zadd key score member score member .....//先判断是否有字母
    public String zadd(String []args) {
        int len=args.length;
        if(len<4||len%2==1)return RespEncoder.error("len<4");
        double score=0;
        Double []value=new Double[(len-2)/2];
        int cnt=0,count=0,s=0;
        for(int i=2;i<len;i=i+2){
            try{value[cnt]=Double.parseDouble(args[i]);}
            catch (NumberFormatException e){
                return RespEncoder.error("");

            }
            cnt++;
        }
        String key=args[1];//单纯的添加
        RedisZset redisZset;
        Map<String, Double> dict ;
        ConcurrentSkipListMap<Double, Set<String>> skiplist;
        if(!map.containsKey(key)){
             redisZset = new RedisZset();
             add(key,redisZset);
        }
        else{
            redisZset=get(key);
        }
        for(int i=3;i<len;i=i+2){
            boolean flag = ZAddOne(redisZset, args[i], value[s]);
            if(flag)count++;
            s++;
        }




        return RespEncoder.integer(count);
    }
    boolean  ZAddOne(RedisZset redisZset,String member,Double score){//对单个成员，score进行操作
          ConcurrentSkipListMap<Double, Set<String>> skiplist = redisZset.getSkiplist();
          Map<String, Double> dict = redisZset.getDict();
          Double oldScore = dict.put(member, score);
          boolean flag=true;
          if (oldScore != null && oldScore.equals(score)) {
                return false; // 分数未变，不修改 --boolean 返回的是它是否额外更新
          }

            // 如果存在旧分数，移除
          if (oldScore != null) {
             Set<String> oldSet = skiplist.get(oldScore);
             oldSet.remove(member);
             if (oldSet.isEmpty()) {
                skiplist.remove(oldScore);
             }
             flag=false;
         }

            // 加入新分数位置
            skiplist.computeIfAbsent(score, k -> new LinkedHashSet<>()).add(member);
            return  flag;
        }







    // 获取分数
    public  String zscore(String[]args) {
        if(args.length!=3)return RespEncoder.error("len!=3");
        String key=args[1];//key
        String member=args[2];
        RedisZset redisZset = get(key);
        if(redisZset==null){
            return RespEncoder.bulkString(null);
        }
        Map<String, Double> dict = getDict(key);
        Double score = dict.get(member);
        if(score==null){ return RespEncoder.bulkString(null);}

        return RespEncoder.bulkString(score.toString());
    }
 // zrange key  start end 有好几种变种
     String ZRANGE(String[]args){//感觉可能问题
         if(args.length!=4)return RespEncoder.error("len!=4");
         int start,end;
         try{
             start=Integer.parseInt(args[2]);
             end=Integer.parseInt(args[3]);

         }
         catch (NumberFormatException e){

             return RespEncoder.error("value is not a number");
         }
         String key=args[1];
         RedisZset redisZset = get(key);
         if(redisZset==null){
             return RespEncoder.array(null);
         }


         List<String> list = zrangeImpl(redisZset, start, end);
         String[] array = list.toArray(new String[0]);
         return RespEncoder.array(array);
     }

    // 返回有序成员（升序）
    public List<String> zrangeImpl( RedisZset redisZset,int start, int end) {
        List<String> result = new ArrayList<>();
        ConcurrentSkipListMap<Double, Set<String>> skiplist = redisZset.getSkiplist();
        for (Set<String> members : skiplist.values()) {
            result.addAll(members);
        }

        int size = result.size();
        if (start < 0) start = size + start;
        if (end < 0) end = size + end;
        end = Math.min(end, size - 1);

        if (start > end || start >= size) return Collections.emptyList();
        return result.subList(start, end + 1);
    }

     String ZREM(String []args){
        int len=args.length;
        if(len<3)return RespEncoder.error("len<3");
        String key=args[1];
        if(!map.containsKey(args[1])){
            return RespEncoder.integer(0);
        }
        RedisZset redisZset = get(key);
        int count=0;
        for(int i=2;i<len;i=i+2){

            if(ZRemOne(redisZset, args[i]))count++;

        }

        return RespEncoder.integer(count);


     }
    // 删除成员
    public boolean ZRemOne( RedisZset redisZset,String member) {
        Map<String, Double> dict = redisZset.getDict();
        ConcurrentSkipListMap<Double, Set<String>> skiplist = redisZset.getSkiplist();
        Double score = dict.remove(member);
        if (score == null) return false;

        Set<String> set = skiplist.get(score);
        if (set != null) {
            set.remove(member);
            if (set.isEmpty()) {
                skiplist.remove(score);
            }
        }
        return true;
    }

//    // 返回成员排名（升序） //返回成员排名，更具升序顺序查询 --实现单member查询
    public String zrank(String[] args) {
        if(args.length!=3)return RespEncoder.error("len<3");
        String key=args[1];
        String member = args[2];
        RedisZset redisZset = get(args[1]);
        if(redisZset==null){
            return RespEncoder.bulkString(null);
        }
        Map<String, Double> dict = redisZset.getDict();
        ConcurrentSkipListMap<Double, Set<String>> skiplist = redisZset.getSkiplist();
        Double score = dict.get(member);
        if (score == null) return null;

        int rank = 0;
        for (Map.Entry<Double, Set<String>> entry : skiplist.entrySet()) {
            for (String m : entry.getValue()) {
                if (m.equals(member)) return RespEncoder.integer(rank);
                rank++;
            }
        }
        return RespEncoder.bulkString(null);
    }
    public void add(String key, RedisZset zset) {//添加key
        RedisObject redisObject = new RedisObject();
        redisObject.setPtr(zset);
        redisObject.setType(RedisType.REDIS_LIST.getType());
        redisObject.setEncoding(RedisEncoding.REDIS_ENCODING_LINKEDLIST.getType());
        map.put(key, redisObject);
    }

    public   Map<String,Double> getDict(String key){
        RedisObject redisObject = map.get(key);
        if( redisObject == null ){ return null; }
        RedisZset ptr = (RedisZset) (redisObject.getPtr());

        return  ptr.getDict();
    }
    public ConcurrentSkipListMap<Double, Set<String>> getSkipList(String key){
        RedisObject redisObject = map.get(key);
        if( redisObject == null ){ return null; }
        RedisZset ptr = (RedisZset) (redisObject.getPtr());

        return  ptr.getSkiplist();
    }
    RedisZset get(String key){
        RedisObject redisObject = map.get(key);
        if( redisObject == null ){ return null; }
        return (RedisZset) (redisObject.getPtr());
    }
}
