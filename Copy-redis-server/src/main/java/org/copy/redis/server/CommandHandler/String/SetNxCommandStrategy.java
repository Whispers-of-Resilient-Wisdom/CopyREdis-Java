package org.copy.redis.server.CommandHandler.String;

import org.copy.redis.server.CommandHandler.String.Enity.StringDS;
import org.copy.redis.server.DataStructure.SDS;
import org.copy.redis.server.interfaces.CommandStrategy;
import org.copy.redis.server.Encoder.RespEncoder;

public class SetNxCommandStrategy extends StringDS implements CommandStrategy
{
    @Override
    public String execute(String[] args) {
        if(args.length != 3) {
            return RespEncoder.error("setnx len!=3");
        }
        String key = args[1];
        String value = args[2];
        if (map.containsKey(key)) return RespEncoder.integer(0);
        add(key,new SDS(value));
        return RespEncoder.integer(1);
    }

}
