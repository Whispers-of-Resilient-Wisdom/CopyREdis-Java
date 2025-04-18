package org.copy.redis.server.CommandHandler.Common;

import org.copy.redis.server.interfaces.CommandStrategy;
import org.copy.redis.server.DataStructure.RedisServerDS;
import org.copy.redis.server.Encoder.RespEncoder;
//返回key存在的个数
public class ExistsCommandStrategy extends RedisServerDS implements CommandStrategy {
    @Override
    public String execute(String[] args) {

        long number=0;
        for(int i=1;i<args.length;i++)
        {
            boolean flag = map.containsKey(args[i]);
            if(flag)number++;
        }
        return RespEncoder.integer(number);

    }
}
