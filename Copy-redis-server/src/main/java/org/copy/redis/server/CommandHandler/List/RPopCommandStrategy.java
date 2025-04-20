package org.copy.redis.server.CommandHandler.List;

import org.copy.redis.server.CommandHandler.List.Enity.ListDS;
import org.copy.redis.server.Encoder.RespEncoder;
import org.copy.redis.server.interfaces.CommandStrategy;

import java.util.LinkedList;

public class RPopCommandStrategy  extends ListDS implements CommandStrategy {
    @Override
    public String execute(String[] args) {
        if(args.length<2)return  RespEncoder.error("len<2");
        String key=args[1];
        String f=null;
        if(map.containsKey(key)){
            LinkedList<String> list = get(key);
            f = list.removeLast();


        }
        return RespEncoder.bulkString(f);
    }
}
