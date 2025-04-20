package org.copy.redis.server.CommandHandler.String.Enity;

import org.copy.redis.server.DataStructure.RedisObject;
import org.copy.redis.server.DataStructure.RedisServerDS;
import org.copy.redis.server.DataStructure.SDS;
import org.copy.redis.server.Enum.RedisEncoding;
import org.copy.redis.server.Enum.RedisType;



public class StringDS  extends RedisServerDS {
    public void add(String key, SDS sds) {//添加key
        RedisObject redisObject = new RedisObject();
        redisObject.setPtr(sds);
        redisObject.setType(RedisType.REDIS_STRING.getType());
        redisObject.setEncoding(RedisEncoding.REDIS_ENCODING_RAW.getType());
        map.put(key, redisObject);
    }

    public StringBuilder get(String key){
        RedisObject redisObject = map.get(key);
        if( redisObject == null ){ return null; }
        SDS ptr = (SDS) (redisObject.getPtr());
        return  ptr.getBuilder();
    }
}
