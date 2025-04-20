package org.copy.redis.server.CommandHandler.List.Enity;

import org.copy.redis.server.DataStructure.RedisLinkedList;
import org.copy.redis.server.DataStructure.RedisObject;
import org.copy.redis.server.DataStructure.RedisServerDS;
import org.copy.redis.server.Enum.RedisEncoding;
import org.copy.redis.server.Enum.RedisType;

import java.util.LinkedList;

public class ListDS extends RedisServerDS{
    public void add(String key, RedisLinkedList list) {//添加key
        RedisObject redisObject = new RedisObject();
        redisObject.setPtr(list);
        redisObject.setType(RedisType.REDIS_LIST.getType());
        redisObject.setEncoding(RedisEncoding.REDIS_ENCODING_LINKEDLIST.getType());
        map.put(key, redisObject);
    }

    public LinkedList<String> get(String key){
        RedisObject redisObject = map.get(key);
        if( redisObject == null ){ return null; }
        RedisLinkedList ptr = (RedisLinkedList) (redisObject.getPtr());
        return  ptr.getList();
    }
}
