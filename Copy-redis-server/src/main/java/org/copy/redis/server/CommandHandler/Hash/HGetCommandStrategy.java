package org.copy.redis.server.CommandHandler.Hash;

import org.copy.redis.server.interfaces.CommandStrategy;
import org.copy.redis.server.DataStructure.RedisDict;
import org.copy.redis.server.DataStructure.RedisObject;
import org.copy.redis.server.DataStructure.RedisServerDS;
import org.copy.redis.server.Encoder.RespEncoder;

import java.util.Map;

public class HGetCommandStrategy extends RedisServerDS implements CommandStrategy {
    @Override
    public String execute(String[] args) {
        if(args.length !=3) {
            return RespEncoder.error("hget len!=3");
        }
        String key=args[1];
        String field=args[2];
        String f="";
        if(!map.containsKey(key)){
            return RespEncoder.error("hget no key");
        }
        RedisObject redisObject = map.get(key);//key

        if (redisObject != null) {

            RedisDict ptr = (RedisDict) (redisObject.getPtr());
            Map<String, String> dict = ptr.getDict();
            if (dict.containsKey(field)) f = dict.get(field);


        }

        return RespEncoder.simpleString(f);
    }
}
