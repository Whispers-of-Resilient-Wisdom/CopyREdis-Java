package org.copy.redis.server.CommandHandler.Hash;

import org.copy.redis.server.CommandHandler.Hash.Enity.HashDS;
import org.copy.redis.server.interfaces.CommandStrategy;

import org.copy.redis.server.Encoder.RespEncoder;

import java.util.Map;

public class HLenCommandStrategy extends HashDS implements CommandStrategy {
    @Override
    public String execute(String[] args) {
        if(args.length != 2) {
            return RespEncoder.error("Hlen len!=2");

        }
        long size;
        String key=args[1];
        if(!map.containsKey(key)){
            return RespEncoder.integer(0);
        }

        Map<String, String> dict = get(key);
        size = dict.size();
        return RespEncoder.integer(size);
    }
}
