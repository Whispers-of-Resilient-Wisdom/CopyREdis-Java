package org.copy.redis.server.CommandHandler.String;


import org.copy.redis.server.CommandHandler.String.Enity.StringDS;
import org.copy.redis.server.Encoder.RespEncoder;
import org.copy.redis.server.interfaces.CommandStrategy;


public class GetCommandStrategy extends StringDS implements CommandStrategy {

    @Override
    public String execute(String[] args) {
        if (args.length !=2) {
            return RespEncoder.error("get length!=2");
        }
        String key = args[1];
        if(!map.containsKey(key))return RespEncoder.bulkString(null);
        StringBuilder stringBuilder = get(key);
        return RespEncoder.bulkString(stringBuilder.toString());

    }
}
