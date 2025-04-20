package org.copy.redis.server.CommandHandler.List;

import org.copy.redis.server.CommandHandler.List.Enity.ListDS;
import org.copy.redis.server.DataStructure.RedisLinkedList;
import org.copy.redis.server.Encoder.RespEncoder;
import org.copy.redis.server.interfaces.CommandStrategy;

import java.util.LinkedList;

public class RPushCommandStrategy extends ListDS implements CommandStrategy {
    @Override
    public String execute(String[] args) {
        if(args.length<3)return  RespEncoder.error("len<3");
        String key=args[1];
        LinkedList<String> list;
        if(!map.containsKey(key)){
            RedisLinkedList redisLinkedList = new RedisLinkedList();
            list= redisLinkedList.getList();
            for(int i=2;i<args.length;i++){

                list.addLast(args[i]);
            }
            add(key,redisLinkedList);


        }
        else{
            list = get(key);
            for(int i=2;i<args.length;i++){

                list.addLast(args[i]);
            }

        }
        return RespEncoder.integer(list.size());
    }
}
