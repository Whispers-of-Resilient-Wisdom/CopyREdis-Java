package org.copy.redis.server.CommandHandler.List;

import org.copy.redis.server.CommandHandler.List.Enity.ListDS;
import org.copy.redis.server.Encoder.RespEncoder;
import org.copy.redis.server.interfaces.CommandStrategy;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class LTrimCommandStrategy extends ListDS implements CommandStrategy {
    @Override
    public String execute(String[] args) {
        if(args.length!=4)return  RespEncoder.error("len!=4");
        String key=args[1];
        long start,stop;
        try {
            start=Long.parseLong(args[2]);
            stop=Long.parseLong(args[3]);
        }
        catch (NumberFormatException e){
            return RespEncoder.error("ERR value is not an integer or out of range");

        }

        if (!map.containsKey(key)) {
            return RespEncoder.simpleString("OK");
        }
        LinkedList<String> list = get(key);
        int size = list.size();
        start = (start < 0) ? size + start : start;
        stop = (stop < 0) ? size + stop : stop;

        start = Math.max(start, 0);
        stop = Math.min(stop, size - 1);

        if (start > stop || start >= size) {
            list.clear();
            return RespEncoder.simpleString("OK");
        }

        List<String> subList = new ArrayList<>(list.subList((int)start, (int)(stop + 1)));
        list.clear();
        list.addAll(subList);
        return RespEncoder.simpleString("OK");
    }
}
