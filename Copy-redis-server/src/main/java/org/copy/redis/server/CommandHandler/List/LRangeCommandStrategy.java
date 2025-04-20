package org.copy.redis.server.CommandHandler.List;

import org.copy.redis.server.CommandHandler.List.Enity.ListDS;
import org.copy.redis.server.Encoder.RespEncoder;
import org.copy.redis.server.interfaces.CommandStrategy;

import java.util.LinkedList;

public class LRangeCommandStrategy   extends ListDS implements CommandStrategy {
    @Override
    public String execute(String[] args) {
        // 处理负数索引
        if(args.length!=4)return  RespEncoder.error("len!=4");
        String key=args[1];
        long start,end;
        try{
            start=Long.parseLong(args[2]);
            end=Long.parseLong(args[3]);
        }
        catch (NumberFormatException e){
            return RespEncoder.error("ERR value is not an integer or out of range");
        }
        if(!map.containsKey(key)){
            return RespEncoder.bulkString(null);

        }
        LinkedList<String> list = get(key);
        if (start < 0) start = list.size() + start;
        if (end < 0) end = list.size() + end;
        start = Math.max(0, start);
        end = Math.min(list.size() - 1, end);
        LinkedList<String> strings = new LinkedList<>(list.subList((int) start, (int)end + 1));

        String[] array = strings.toArray(new String[0]);
        return RespEncoder.array(array);
    }
}
