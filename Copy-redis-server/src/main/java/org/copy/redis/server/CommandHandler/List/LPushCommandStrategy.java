package org.copy.redis.server.CommandHandler.List;

import org.copy.redis.server.CommandHandler.List.Enity.ListDS;
import org.copy.redis.server.DataStructure.RedisLinkedList;
import org.copy.redis.server.Encoder.RespEncoder;
import org.copy.redis.server.interfaces.CommandStrategy;

import java.util.Arrays;
import java.util.LinkedList;

public class LPushCommandStrategy extends ListDS implements CommandStrategy {
    @Override
    public String execute(String[] args) {
        if(args.length<3)return RespEncoder.error("len<3");
        String key=args[1];
        LinkedList<String > list;
        if(!map.containsKey(key)){
            RedisLinkedList redisLinkedList = new RedisLinkedList();
            list = redisLinkedList.getList();
            list.addAll(Arrays.asList(args).subList(2, args.length));
            add(key,redisLinkedList);
        }
        else {
            list = get(key);
            list.addAll(Arrays.asList(args).subList(2, args.length));

        }

        return RespEncoder.integer(list.size()); // 返回插入的元素数量
    }
}
