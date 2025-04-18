package org.copy.redis.server.CommandHandler.Common;

import org.copy.redis.server.interfaces.CommandStrategy;
import org.copy.redis.server.DataStructure.RedisServerDS;
import org.copy.redis.server.Encoder.RespEncoder;

import java.time.Instant;
//  EXPIRE key seconds //1表示成功，0表示键不存在
public class ExpireCommandStrategy extends RedisServerDS implements CommandStrategy {
    @Override
    public String execute(String[] args) {
        if (args.length != 3) {
            return RespEncoder.simpleString("error  length!=2");

        }
        String key = args[1];
        try {
            int seconds = Integer.parseInt(args[2]);
            int number = 0;
            if (map.containsKey(key)) {
                number = 1;
                long now = Instant.now().getEpochSecond();
                long exxistsTime = now + seconds;
                expire.put(key, exxistsTime);
            }
            return RespEncoder.integer(number);
        } catch (NumberFormatException e) {
            return RespEncoder.error("seconds is not a number");

        }

    }
}
