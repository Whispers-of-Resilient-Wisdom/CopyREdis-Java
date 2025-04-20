package org.copy.redis.server.CommandHandler.Hash;


import org.copy.redis.server.CommandHandler.Hash.Enity.HashDS;
import org.copy.redis.server.interfaces.CommandStrategy;

import org.copy.redis.server.Encoder.RespEncoder;

import java.util.Collection;
import java.util.Map;

public class HvalsCommandStrategy extends HashDS implements CommandStrategy {
    @Override
    public String execute(String[] args) {
        if(args.length!=2){
            return RespEncoder.error("hvals len!=2");
        }
        String key = args[1];
        if(!map.containsKey(key)){
            return RespEncoder.integer(0);
        }

        String []f;
        Map<String, String> dict = get(key);
        int size = dict.size();
        f=new String[size];
        int i=0;
        Collection<String> values = dict.values();
        for (String value : values) {
                f[i]=value;
                i++;
        }

        return RespEncoder.array(f);

    }
}

