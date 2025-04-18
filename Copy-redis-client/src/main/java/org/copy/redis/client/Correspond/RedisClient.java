package org.copy.redis.client.Correspond;
import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;


public class RedisClient {
//    private Socket socket;
//    static   private BufferedReader reader;
//     static private BufferedWriter writer;
//
//    public RedisClient(String host, int port) throws IOException {
//        socket = new Socket(host, port);
//        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//        writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
//    }
//
//
//
//
//    public void close() throws IOException {
//        socket.close();
//    }
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//    //append key value
//    public void APPEND(String key,String value) throws IOException {
//        sendCommand("APPEND", key,value);
//        System.out.println("Server: " + readResponse());
//
//    }
//
//    //STRLEN key ( 求长度 length)
//    public void STRLEN(String key) throws IOException {
//        sendCommand("STRLEN", key);
//        System.out.println("Server: " + readResponse());
//
//    }
//    //todo LPUSH key value1 value2 ...
//    public void LPUSH(String key, List<String > value) throws IOException {
//        sendCommand("LPUSH", key);
//        System.out.println("Server: " + readResponse());
//
//    }
//    //RPUSH key value1 value2 ...
//    public void RPUSH(String key, List<String > value) throws IOException {
//        sendCommand("RPUSH", key);
//        System.out.println("Server: " + readResponse());
//
//    }
//    //LPOP key
//    public void LPOP(String key) throws IOException {
//        sendCommand("LPOP", key);
//        System.out.println("Server: " + readResponse());
//
//    }
//    //RPOP key 移除并返回列表尾部的元素
//    public void RPOP(String key) throws IOException {
//        sendCommand("RPOP", key);
//        System.out.println("Server: " + readResponse());
//
//    }
//    //LRANGE key start stop
//    public void LRANGE(String key,int start ,int stop) throws IOException {
//        sendCommand("LRANGE", key,Integer.toString(start),Integer.toString(stop));
//        System.out.println("Server: " + readResponse());
//
//    }
//    //LLEN key	获取列表长度
//    public void LLEN(String key) throws IOException {
//        sendCommand("LLEN", key);
//        System.out.println("Server: " + readResponse());
//
//    }
//    //LREM key count value	删除列表中指定的值
//    public void LREM(String key,int count,int value) throws IOException {
//        sendCommand("LREM", key,Integer.toString(count),Integer.toString(value));
//        System.out.println("Server: " + readResponse());
//
//    }
//    //LINDEX key index	获取列表中指定索引的元素
//    public void INDEX(String key,int index) throws IOException {
//        sendCommand("INDEX", key,Integer.toString(index));
//        System.out.println("Server: " + readResponse());
//
//    }
//   //  todo  SADD key member1 member2 ...	向集合添加元素
//   public void INDEX(String key,List<String> member) throws IOException {
//    System.out.println("Server: " + readResponse());
//
//    }
//   //    SREM key member	删除集合中的元素
//
//    public void SREM(String key,String member) throws IOException {
//        sendCommand("INDEX", key,member);
//        System.out.println("Server: " + readResponse());
//
//    }
//   //    SMEMBERS key	获取集合中的所有元素
//   public void SMEMBERS(String key) throws IOException {
//    sendCommand("SMEMBERS", key);
//    System.out.println("Server: " + readResponse());
//
//   }
//    //    SISMEMBER key member	判断元素是否存在于集合中
//   public void SISMEMBER(String key,String member) throws IOException {
//       sendCommand("SISMEMBER", key,member);
//       System.out.println("Server: " + readResponse());
//
//   }
//   // SCARD key 查看集合大小
//    public void SCARD(String key) throws IOException {
//        sendCommand("SCARD", key);
//        System.out.println("Server: " + readResponse());
//
//    }
//    //    SRANDMEMBER key [count]	随机返回集合中的一个或多个元素
//    public void SRANDMEMBER(String key,int count) throws IOException {
//        sendCommand("SRANDMEMBER", key,Integer.toString(count));
//        System.out.println("Server: " + readResponse());
//
//    }
//    //    SDIFF key1 key2	返回 key1 与 key2 的差集
//    public void SDIFF(String key1,String key2) throws IOException {
//        sendCommand("SDIFF", key1,key2);
//        System.out.println("Server: " + readResponse());
//
//    }
//    //    SINTER key1 key2	返回 key1 与 key2 的交集
//    public void SINTER(String key1,String key2) throws IOException {
//        sendCommand("SINTER", key1,key2);
//        System.out.println("Server: " + readResponse());
//
//    }
//    //    SUNION key1 key2	返回 key1 与 key2 的并集
//    public void SUNION(String key1,String key2) throws IOException {
//        sendCommand("SUNION", key1,key2);
//        System.out.println("Server: " + readResponse());
//
//    }
////    ZADD key score1 member1 score2 member2 ...	添加元素到有序集合并设置分数
////    ZREM key member	删除有序集合中的元素
////    ZCARD key	获取有序集合中的元素数量
////    ZRANGE key start stop [WITHSCORES]	按分数从低到高获取元素
////    ZREVRANGE key start stop [WITHSCORES]	按分数从高到低获取元素
////    ZSCORE key member	获取元素的分数
////    ZRANK key member	获取元素的排名（从 0 开始）
////    ZREVRANK key member	获取元素的倒序排名
////    ZINCRBY key increment member	增加某个元素的分数
//
//
//    public static void main(String[] args) throws IOException {
//        RedisClient client = new RedisClient("localhost", 6379);
//        client.set("word", "沙雕");
//    System.out.println(client.get("word"));
//        System.out.println("GET hello: " + client.get("word"));
//
//        try {
//            Thread.sleep(2000);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
//        System.out.println("GET hello: " + client.get("word"));
//        client.close();
//    }
}

