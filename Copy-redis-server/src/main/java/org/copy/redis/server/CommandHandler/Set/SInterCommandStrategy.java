package org.copy.redis.server.CommandHandler.Set;

import org.copy.redis.server.CommandHandler.Set.Enity.SetDS;
import org.copy.redis.server.DataStructure.RedisHashTable;
import org.copy.redis.server.DataStructure.RedisObject;
import org.copy.redis.server.Encoder.RespEncoder;
import org.copy.redis.server.interfaces.CommandStrategy;

import java.util.HashSet;
import java.util.Set;

public class SInterCommandStrategy extends SetDS implements CommandStrategy {
    @Override
    public String execute(String[] args) {
        if(args.length < 2) {
            return RespEncoder.error("len<2");
        }
        boolean flag=false;
        for(int i=1;i<args.length;i++){
            if(!map.containsKey(args[i])) {
                flag=true;
                break;

            }

        }
        if(flag) return RespEncoder.bulkString(null);
        Set<String> inter = new HashSet<>();

        for(int i=1;i<args.length;i++) {
            RedisObject redisObject = map.get(args[1]);

            RedisHashTable ptr = (RedisHashTable) (redisObject.getPtr());
            Set<String> set = ptr.getSet();
            if(i==1)inter.addAll(set);
            else inter.retainAll(set);
        }
        return RespEncoder.array(inter.toArray(new String[0]));
    }
}
