package org.copy.redis.server.CommandHandler.Common;


import org.copy.redis.server.interfaces.CommandStrategy;
import org.copy.redis.server.DataStructure.RedisServerDS;
import org.copy.redis.server.Encoder.RespEncoder;
//删除节点
public class DelCommandStrategy extends RedisServerDS implements CommandStrategy {
    @Override
    public String execute(String[] args) {

        long number=0;
        for(int i=1;i<args.length;i++){
            if(map.containsKey(args[i])){ map.remove(args[i]);
                expire.remove(args[i]);
                number++;
            }


        }
        return RespEncoder.integer(number);



    }
}
