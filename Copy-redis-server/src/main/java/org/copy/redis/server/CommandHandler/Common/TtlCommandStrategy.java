package org.copy.redis.server.CommandHandler.Common;

import org.copy.redis.server.interfaces.CommandStrategy;
import org.copy.redis.server.DataStructure.RedisServerDS;
import org.copy.redis.server.Encoder.RespEncoder;

import java.time.Instant;

//查看ttl时间
public class TtlCommandStrategy extends RedisServerDS implements CommandStrategy {
    @Override
    public String execute(String[] args) {
        if(args.length != 2) {

            return RespEncoder.error("ttl lerngth!=2");
        }
        String key=args[1];
        long number;
        boolean  exists= map.containsKey(key);
        if(!exists){number=-2; return RespEncoder.integer(number);}
        boolean flag = expire.containsKey(key);
        if(flag)
        {
            long l = expire.get(key)/1000L;
            long seconds = Instant.now().getEpochSecond();
            number= l-seconds;

        }
        else number=-1;
        return RespEncoder.integer(number);

    }
}
