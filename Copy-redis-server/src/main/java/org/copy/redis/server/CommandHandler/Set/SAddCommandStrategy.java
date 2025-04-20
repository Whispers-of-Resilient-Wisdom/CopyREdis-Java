package org.copy.redis.server.CommandHandler.Set;

import org.copy.redis.server.CommandHandler.Set.Enity.SetDS;
import org.copy.redis.server.Encoder.RespEncoder;
import org.copy.redis.server.interfaces.CommandStrategy;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class SAddCommandStrategy extends SetDS implements CommandStrategy {
    @Override
    public String execute(String[] args) {
        if (args.length < 3 ) {
            return RespEncoder.error("sadd len<3");
        }
        String key=args[1];
        long count=0;
        Set<String > set;
        if(!map.containsKey(key)){

            set = new HashSet<>(Arrays.asList(args).subList(2, args.length));
            add(key,set);
            count= (args.length-2);
        }else{
            set = get(key);
            for(int i=2;i<args.length;i++){
                boolean flag= set.contains(args[i]);
                if(!flag)
                {count++;
                    set.add(args[i]);}

            }




        }


        return RespEncoder.integer(count);
    }
}
