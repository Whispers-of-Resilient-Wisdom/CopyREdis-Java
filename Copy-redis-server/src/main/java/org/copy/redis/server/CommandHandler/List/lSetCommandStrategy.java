package org.copy.redis.server.CommandHandler.List;

import org.copy.redis.server.CommandHandler.List.Enity.ListDS;
import org.copy.redis.server.Encoder.RespEncoder;
import org.copy.redis.server.interfaces.CommandStrategy;

import java.util.LinkedList;

public class lSetCommandStrategy  extends ListDS implements CommandStrategy {
    @Override
    public String execute(String[] args) {
        if(args.length!=4)return  RespEncoder.error("len!=3");
        String key=args[1];
        long index;
        try{
            index=Long.parseLong(args[2]);
        }
        catch (NumberFormatException e){
            return RespEncoder.error("ERR value is not an integer or out of range");
        }
        if(!map.containsKey(key)){
            return RespEncoder.error("ERR no such key");

        }
        LinkedList<String> list = get(key);
        index = (index < 0) ? list.size()+ index : index;
        if (index < 0 || index >= list.size()) return  RespEncoder.error("ERR index out of range");
        list.set((int)index, args[3]);
        return RespEncoder.simpleString("OK");

    }
}
