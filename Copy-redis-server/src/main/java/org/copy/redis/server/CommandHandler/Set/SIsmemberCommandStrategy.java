package org.copy.redis.server.CommandHandler.Set;

import org.copy.redis.server.CommandHandler.Set.Enity.SetDS;
import org.copy.redis.server.Encoder.RespEncoder;
import org.copy.redis.server.interfaces.CommandStrategy;

import java.util.Set;

public class SIsmemberCommandStrategy extends SetDS implements CommandStrategy {
    @Override
    public String execute(String[] args) {


            if (args.length != 3) {
                return RespEncoder.error("len!=3");
            }
            long count = 0;
            String key = args[1];
            String member = args[2];
            if (map.containsKey(key)) {
                Set<String> set = get(key);
                boolean flag = set.contains(member);
                if (flag) count++;
            }


            return RespEncoder.integer(count);
        }
    }

