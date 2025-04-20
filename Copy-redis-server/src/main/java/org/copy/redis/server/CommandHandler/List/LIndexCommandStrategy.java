package org.copy.redis.server.CommandHandler.List;

import org.copy.redis.server.CommandHandler.List.Enity.ListDS;
import org.copy.redis.server.Encoder.RespEncoder;
import org.copy.redis.server.interfaces.CommandStrategy;

import java.util.LinkedList;

public class LIndexCommandStrategy  extends ListDS implements CommandStrategy {
    @Override
    public String execute(String[] args) {
        if(args.length!=3)return  RespEncoder.error("len!=3");
        String key=args[1];
        int index;
        try{
            index=Integer.parseInt(args[2]);
        }
        catch (NumberFormatException e){
            return RespEncoder.error("ERR value is not an integer or out of range");
        }
        if(!map.containsKey(key)){
            return RespEncoder.bulkString(null);

        }
        LinkedList<String> list = get(key);
        String f=null;
        if (index<0)  {
            index=list.size()+index;
            if(index<0) index=0;
        }
        if(index<list.size())f=list.get(index);

        return RespEncoder.bulkString(f);
    }
}
