package org.copy.redis.server.CommandHandler.String;

import org.copy.redis.server.DataStructure.RedisObject;
import org.copy.redis.server.DataStructure.RedisServerDS;
import org.copy.redis.server.DataStructure.SDS;
import org.copy.redis.server.Encoder.RespEncoder;
import org.copy.redis.server.Enum.RedisEncoding;
import org.copy.redis.server.Enum.RedisType;
import org.copy.redis.server.interfaces.CommandStrategy;

public class IncreByCommandStrategy extends RedisServerDS implements CommandStrategy {
    @Override
    public String execute(String[] args) {
        if(args.length != 3) {
            return RespEncoder.error(" len!=3");
        }
        try {
            String key = args[1];
            String value = args[2];
            long i = Integer.parseInt(value);
            boolean flag=map.containsKey(key);
            if(!flag) set(key, value);
            if (flag) {
                RedisObject redisObject = map.get(key);
                i += Integer.parseInt(((StringBuilder)redisObject.getPtr()).toString());
            }
            return RespEncoder.integer(i);
        }catch (Exception e) {


            return RespEncoder.integer(-1);//是指value不是合法整数

        }

    }
    static void set(String key, String value) {
        RedisObject redisObject = new RedisObject();
        redisObject.setType(RedisType.REDIS_STRING.getType());
        redisObject.setEncoding(RedisEncoding.REDIS_ENCODING_RAW.getType());//待处理
        redisObject.setPtr(new SDS(value));
        map.put(key, redisObject);
    }
}

