package org.copy.redis.server.CommandHandler.Hash;

import org.copy.redis.server.CommandHandler.Hash.Enity.HashDS;
import org.copy.redis.server.interfaces.CommandStrategy;


import org.copy.redis.server.Encoder.RespEncoder;

import java.util.Map;
import java.util.Set;

public class HKeysCommandStrategy extends HashDS implements CommandStrategy {
    @Override
    public String execute(String[] args) {
        if(args.length != 2) {

            return RespEncoder.error("Hkeys len!=2");
        }
        String key = args[1];
        if(!map.containsKey(key)){
            return RespEncoder.error("Hkeys not exist");
        }
        String []f;
        Map<String, String> dict = get(key);
        int size = dict.size();
        f=new String[size];
        int i=0;
        Set<String> strings = dict.keySet();
        for (String value : strings) {
                f[i]=value;
                i++;
        }


        return RespEncoder.array(f);



    }
}

