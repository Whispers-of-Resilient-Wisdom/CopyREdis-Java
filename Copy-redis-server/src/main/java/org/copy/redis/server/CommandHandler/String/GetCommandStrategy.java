package org.copy.redis.server.CommandHandler.String;


import org.copy.redis.server.DataStructure.RedisObject;
import org.copy.redis.server.DataStructure.RedisServerDS;
import org.copy.redis.server.Encoder.RespEncoder;
import org.copy.redis.server.interfaces.CommandStrategy;

public class GetCommandStrategy extends RedisServerDS implements CommandStrategy {

    @Override
    public String execute(String[] args) {
        if (args.length !=2) {
            return RespEncoder.error("get length!=2");
        }
        String key = args[1];
        RedisObject redisObject = map.get(key);
        String f = "";
        if (redisObject != null) f = ((StringBuilder) redisObject.getPtr()).toString();
        return RespEncoder.simpleString(f);

    }
}
