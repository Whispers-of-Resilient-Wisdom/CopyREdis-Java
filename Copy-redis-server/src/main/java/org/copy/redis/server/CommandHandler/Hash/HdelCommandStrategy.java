package org.copy.redis.server.CommandHandler.Hash;

import org.copy.redis.server.CommandHandler.Hash.Enity.HashDS;
import org.copy.redis.server.interfaces.CommandStrategy;

import org.copy.redis.server.Encoder.RespEncoder;


import java.util.Map;
//ddel
public class HdelCommandStrategy extends HashDS implements CommandStrategy {
    @Override
    public String execute(String[] args) {
        String key=args[1];
        long count=0;
        if(map.containsKey(key)) {

            Map<String, String> dict = get(key);
            for (int i = 2; i < args.length; i ++) {
                boolean flag = dict.containsKey(args[i]);
                if (flag) count++;
                dict.remove(args[i]);

            }

        }


        return RespEncoder.integer(count);
    }
}
