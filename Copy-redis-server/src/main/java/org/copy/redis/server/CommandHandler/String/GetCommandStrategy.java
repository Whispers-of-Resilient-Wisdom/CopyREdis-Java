package org.copy.redis.server.CommandHandler.String;


import org.copy.redis.server.DataStructure.RedisObject;
import org.copy.redis.server.DataStructure.RedisServerDS;
import org.copy.redis.server.DataStructure.SDS;
import org.copy.redis.server.Encoder.RespEncoder;
import org.copy.redis.server.interfaces.CommandStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GetCommandStrategy extends RedisServerDS implements CommandStrategy {

    @Override
    public String execute(String[] args) {
        if (args.length !=2) {
            return RespEncoder.error("get length!=2");
        }
        String key = args[1];
        RedisObject redisObject = map.get(key);
        String f = null;
        if (redisObject != null) f = ((SDS)(redisObject.getPtr())).getBuilder().toString();

        return RespEncoder.bulkString(f);

    }
}
