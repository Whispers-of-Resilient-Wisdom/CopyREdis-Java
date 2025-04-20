package org.copy.redis.server.CommandHandler.Set;

import org.copy.redis.server.CommandHandler.Set.Enity.SetDS;
import org.copy.redis.server.Encoder.RespEncoder;
import org.copy.redis.server.interfaces.CommandStrategy;

import java.util.Set;

public class SMembersCommandStrategy extends SetDS implements CommandStrategy {
    @Override
    public String execute(String[] args) {
        if(args.length != 2) {

            return RespEncoder.error("len!=2");
        }
        String key = args[1];
        String []array = null;
        if(map.containsKey(key)) {
            Set<String> set = get(key);
            array = set.toArray(new String[0]);

        }

        return RespEncoder.array(array);
    }
}
