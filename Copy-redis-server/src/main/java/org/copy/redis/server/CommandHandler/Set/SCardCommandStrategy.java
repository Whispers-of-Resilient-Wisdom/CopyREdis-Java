package org.copy.redis.server.CommandHandler.Set;

import org.copy.redis.server.CommandHandler.Set.Enity.SetDS;
import org.copy.redis.server.Encoder.RespEncoder;
import org.copy.redis.server.interfaces.CommandStrategy;

import java.util.Set;

public class SCardCommandStrategy extends SetDS implements CommandStrategy {
    @Override
    public String execute(String[] args) {
        if(args.length != 2) {
            return RespEncoder.error("SCRD len!=2");

        }
        long size;
        String key=args[1];
        if(!map.containsKey(key)){
            size=0;
            return RespEncoder.integer(size);
        }

        Set<String> set = get(key);
        size = set.size();



        return RespEncoder.integer(size);
    }
}
