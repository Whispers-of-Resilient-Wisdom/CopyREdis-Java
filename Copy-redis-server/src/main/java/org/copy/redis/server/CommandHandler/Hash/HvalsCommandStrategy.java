package org.copy.redis.server.CommandHandler.Hash;


import org.copy.redis.server.interfaces.CommandStrategy;
import org.copy.redis.server.DataStructure.RedisDict;
import org.copy.redis.server.DataStructure.RedisObject;
import org.copy.redis.server.DataStructure.RedisServerDS;
import org.copy.redis.server.Encoder.RespEncoder;

import java.util.Collection;
import java.util.Map;

public class HvalsCommandStrategy extends RedisServerDS implements CommandStrategy {
    @Override
    public String execute(String[] args) {
        if(args.length!=2){
            return RespEncoder.error("hvals len!=2");
        }
        String key = args[1];
        if(!map.containsKey(key)){
            return RespEncoder.integer(0);
        }
        RedisObject redisObject = map.get(key);//key
        String []f=null;
        if (redisObject != null) {


            RedisDict ptr = (RedisDict) (redisObject.getPtr());
            Map<String, String> dict = ptr.getDict();
            int size = dict.size();
            f=new String[size];
            int i=0;
            Collection<String> values = dict.values();
            for (String value : values) {
                f[i]=value;
                i++;
            }



        }

        return RespEncoder.array(f);

    }
}

