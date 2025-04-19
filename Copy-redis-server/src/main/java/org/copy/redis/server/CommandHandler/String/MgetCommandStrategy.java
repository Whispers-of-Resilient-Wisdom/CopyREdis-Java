package org.copy.redis.server.CommandHandler.String;

import org.copy.redis.server.interfaces.CommandStrategy;
import org.copy.redis.server.DataStructure.RedisObject;
import org.copy.redis.server.DataStructure.RedisServerDS;
import org.copy.redis.server.Encoder.RespEncoder;

public class MgetCommandStrategy extends RedisServerDS implements CommandStrategy {
    @Override
    public String execute(String[] args) {
        if(args.length <2 ) {
            return RespEncoder.error("Mget len<2");
        }
        int len = args.length;
        String[] value = new String[len-1];
        for (int i = 1; i < args.length; i++) {
            value[i] = get(args[i]);


        }


        return RespEncoder.array(value);
    }

    static String get(String arg) {

        RedisObject redisObject = map.get(arg);
        String f = null;
        if (redisObject != null) f = ((StringBuilder) redisObject.getPtr()).toString();
        return f;

    }
}
