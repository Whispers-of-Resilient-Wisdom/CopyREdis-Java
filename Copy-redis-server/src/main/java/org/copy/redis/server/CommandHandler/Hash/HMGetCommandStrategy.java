package org.copy.redis.server.CommandHandler.Hash;

import org.copy.redis.server.interfaces.CommandStrategy;
import org.copy.redis.server.DataStructure.RedisDict;
import org.copy.redis.server.DataStructure.RedisObject;
import org.copy.redis.server.DataStructure.RedisServerDS;
import org.copy.redis.server.Encoder.RespEncoder;

import java.util.Map;

public class HMGetCommandStrategy extends RedisServerDS implements CommandStrategy {
    @Override
    public String execute(String[] args) {
        if(args.length <3) {
            return RespEncoder.error("Hmget len<3");
        }
        String key = args[1];
        if(!map.containsKey(key)){
            return RespEncoder.error("Hmget no key");
        }
        RedisObject redisObject = map.get(args[1]);//key

        RedisDict ptr = (RedisDict) (redisObject.getPtr());
        Map<String, String> dict = ptr.getDict();
        String []f=new String[args.length-2];
        for(int i=2;i<args.length;i++) {


            f[i] = dict.getOrDefault(args[i], "");
        }



        return RespEncoder.array(f);
    }
}
