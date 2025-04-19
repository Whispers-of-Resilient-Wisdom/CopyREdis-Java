package org.copy.redis.server.CommandHandler.Common;

import org.copy.redis.server.DataStructure.RedisServerDS;
import org.copy.redis.server.Encoder.RespEncoder;
import org.copy.redis.server.interfaces.CommandStrategy;

public class FlushAllCommandStrategy extends RedisServerDS implements CommandStrategy {
    @Override
    public String execute(String[] args) {
        map.clear();
        expire.clear();
        return RespEncoder.simpleString("OK");
    }
}
