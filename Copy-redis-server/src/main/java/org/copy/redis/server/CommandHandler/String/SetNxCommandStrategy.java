package org.copy.redis.server.CommandHandler.String;

import org.copy.redis.server.interfaces.CommandStrategy;
import org.copy.redis.server.DataStructure.RedisObject;
import org.copy.redis.server.DataStructure.RedisServerDS;
import org.copy.redis.server.DataStructure.SDS;
import org.copy.redis.server.Encoder.RespEncoder;
import org.copy.redis.server.Enum.RedisEncoding;
import org.copy.redis.server.Enum.RedisType;

public class SetNxCommandStrategy extends RedisServerDS implements CommandStrategy
{
    @Override
    public String execute(String[] args) {
        if(args.length != 3) {
            return RespEncoder.error("setnx len!=3");
        }
        String key = args[1];
        String value = args[2];

        if (map.containsKey(key)) return RespEncoder.integer(0);

        set(key, value);

        return RespEncoder.integer(1);
    }
    static void set(String key, String value) {
        RedisObject redisObject = new RedisObject();
        redisObject.setType(RedisType.REDIS_STRING.getType());
        redisObject.setEncoding(RedisEncoding.REDIS_ENCODING_RAW.getType());//待处理
        redisObject.setPtr(new SDS(value));
        map.put(key, redisObject);
    }
}
