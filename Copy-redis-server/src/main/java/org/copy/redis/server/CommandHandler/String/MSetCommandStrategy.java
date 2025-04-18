package org.copy.redis.server.CommandHandler.String;

import org.copy.redis.server.interfaces.CommandStrategy;
import org.copy.redis.server.DataStructure.RedisObject;
import org.copy.redis.server.DataStructure.RedisServerDS;
import org.copy.redis.server.DataStructure.SDS;
import org.copy.redis.server.Encoder.RespEncoder;
import org.copy.redis.server.Enum.RedisEncoding;
import org.copy.redis.server.Enum.RedisType;


public class MSetCommandStrategy  extends RedisServerDS implements CommandStrategy {
    @Override
    public String execute(String[] args) {
        int len = args.length;
        if(len<2||len %2==0)return RespEncoder.error("len<2||len%2==0");


        for (int i = 1; i < len; i = i + 2) {
            set(args[i], args[i + 1]);

        }
        return RespEncoder.simpleString("OK");
    }

    static void set(String key, String value) {
        RedisObject redisObject = new RedisObject();
        redisObject.setType(RedisType.REDIS_STRING.getType());
        redisObject.setEncoding(RedisEncoding.REDIS_ENCODING_RAW.getType());//待处理
        redisObject.setPtr(new SDS(value));
        map.put(key, redisObject);
    }

}