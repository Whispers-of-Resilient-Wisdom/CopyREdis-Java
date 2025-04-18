package org.copy.redis.server.CommandHandler.Hash;

import org.copy.redis.server.interfaces.CommandStrategy;
import org.copy.redis.server.DataStructure.RedisDict;
import org.copy.redis.server.DataStructure.RedisObject;
import org.copy.redis.server.DataStructure.RedisServerDS;
import org.copy.redis.server.Encoder.RespEncoder;

import java.util.Map;
//查看字段是否存在
public class HExistsCommandStrategy extends RedisServerDS implements CommandStrategy {
    @Override
    public String execute(String[] args) {
        if(args.length != 3) {
            return RespEncoder.error("Wrong number of arguments");
        }
        long count=0;
        String key=args[1];
        String field=args[2];
        if(map.containsKey(key)) {
            RedisObject redisObject =  map.get(key);
            RedisDict ptr = (RedisDict) (redisObject.getPtr());
            Map<String, String> dict = ptr.getDict();
            boolean flag = dict.containsKey(field);
            if (flag) count++;
        }


        return RespEncoder.integer(count);
    }
}
