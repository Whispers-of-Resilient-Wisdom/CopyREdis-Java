package org.copy.redis.server.CommandHandler.Hash;

import org.copy.redis.server.interfaces.CommandStrategy;
import org.copy.redis.server.DataStructure.RedisDict;
import org.copy.redis.server.DataStructure.RedisObject;
import org.copy.redis.server.DataStructure.RedisServerDS;
import org.copy.redis.server.Encoder.RespEncoder;

import java.util.Map;

public class HLenCommandStrategy extends RedisServerDS implements CommandStrategy {
    @Override
    public String execute(String[] args) {
        if(args.length != 2) {
            return RespEncoder.error("Hlen len!=2");

        }
        long size=-1;
        String key=args[1];
        if(!map.containsKey(key)){
            size=0;
        }
        RedisObject redisObject = map.get(key);//key

        if (redisObject != null) {
            RedisDict ptr = (RedisDict) (redisObject.getPtr());
            Map<String, String> dict = ptr.getDict();
            size = dict.size();


        }

        return RespEncoder.integer(size);
    }
}
