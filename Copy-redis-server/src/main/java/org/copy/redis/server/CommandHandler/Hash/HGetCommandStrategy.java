package org.copy.redis.server.CommandHandler.Hash;

import org.copy.redis.server.CommandHandler.Hash.Enity.HashDS;
import org.copy.redis.server.interfaces.CommandStrategy;
import org.copy.redis.server.Encoder.RespEncoder;

import java.util.Map;
//hget key field
public class HGetCommandStrategy extends HashDS implements CommandStrategy {
    @Override
    public String execute(String[] args) {
        if(args.length !=3) {
            return RespEncoder.error("hget len!=3");
        }
        String key=args[1];
        String field=args[2];
        String f=null;
        if(!map.containsKey(key)){
            return RespEncoder.error("hget no key");
        }
        Map<String, String> dict = get(key);
        if (dict.containsKey(field)) f = dict.get(field);
        return RespEncoder.bulkString(f);
    }
}
