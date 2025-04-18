package org.copy.redis.server.CommandHandler.Hash;

import org.copy.redis.server.interfaces.CommandStrategy;
import org.copy.redis.server.DataStructure.RedisDict;
import org.copy.redis.server.DataStructure.RedisObject;
import org.copy.redis.server.DataStructure.RedisServerDS;
import org.copy.redis.server.Encoder.RespEncoder;

import java.util.Map;

public class HdelCommandStrategy extends RedisServerDS implements CommandStrategy {
    @Override
    public String execute(String[] args) {
        String key=args[1];
        int count=0;


        if(map.containsKey(key)) {
            RedisObject redisObject = map.get(key);
            RedisDict ptr = (RedisDict) (redisObject.getPtr());
            Map<String, String> dict = ptr.getDict();
            for (int i = 2; i < args.length; i ++) {
                boolean flag = dict.containsKey(args[i]);
                if (flag) count++;
                dict.remove(args[i]);

            }
        }


        return RespEncoder.integer(count);
    }
}
