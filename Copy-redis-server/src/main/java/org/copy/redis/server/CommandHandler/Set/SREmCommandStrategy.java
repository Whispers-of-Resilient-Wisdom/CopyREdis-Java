package org.copy.redis.server.CommandHandler.Set;

import org.copy.redis.server.CommandHandler.Set.Enity.SetDS;
import org.copy.redis.server.Encoder.RespEncoder;
import org.copy.redis.server.interfaces.CommandStrategy;

import java.util.Set;

public class SREmCommandStrategy extends SetDS implements CommandStrategy {
    @Override
    public String execute(String[] args) {
        String key=args[1];
        long count=0;

        if(map.containsKey(key)) {
            Set<String> set = get(key);
            for (int i = 2; i < args.length; i ++) {
                boolean flag = set.contains(args[i]);
                if (flag)
                {count++;set.remove(args[i]);}
            }

        }

        return RespEncoder.integer(count);
    }
}
