package org.copy.redis.server.CommandHandler.Hash.Enity;

import org.copy.redis.server.DataStructure.RedisDict;
import java.util.Map;
import org.copy.redis.server.DataStructure.RedisObject;
import org.copy.redis.server.DataStructure.RedisServerDS;
import org.copy.redis.server.Enum.RedisEncoding;
import org.copy.redis.server.Enum.RedisType;



public class HashDS extends RedisServerDS {
    public void add(String key ,RedisDict dict) {
        RedisObject redisObject = new RedisObject();
        redisObject.setType(RedisType.REDIS_HASH.getType());
        redisObject.setEncoding(RedisEncoding.REDIS_ENCODING_HT.getType());
        redisObject.setPtr(dict);
        map.put(key, redisObject);
    }

    public Map <String,String> get(String key){
        RedisObject redisObject = map.get(key);
        RedisDict ptr = (RedisDict) (redisObject.getPtr());
        return ptr.getDict();

    }
}
