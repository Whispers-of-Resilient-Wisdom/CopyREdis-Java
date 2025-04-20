package org.copy.redis.server.CommandHandler.Hash;

import org.copy.redis.server.CommandHandler.Hash.Enity.HashDS;
import org.copy.redis.server.interfaces.CommandStrategy;
import org.copy.redis.server.Encoder.RespEncoder;

import java.util.Map;

public class HGetAllCommandStrategy extends HashDS implements CommandStrategy {
    @Override
    public String execute(String[] args) {
        if(args.length != 2) {
            return RespEncoder.error("Hgetall len!=2");
        }
        String key = args[1];
        if(!map.containsKey(key)){
            return RespEncoder.error("key not found");
        }

        String []f;

        Map<String, String> dict = get(key);
        int size = dict.size();
        f=new String[2*size];
        int i=0;
        for (Map.Entry<String, String> entry : dict.entrySet()) {
            String keys = entry.getKey();
            String value = entry.getValue();
            f[i]=keys;
            f[i+1]=value;
            i=i+2;
        }




        return RespEncoder.array(f);

    }
}
