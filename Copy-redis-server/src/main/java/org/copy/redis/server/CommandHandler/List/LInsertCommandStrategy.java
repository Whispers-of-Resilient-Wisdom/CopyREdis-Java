package org.copy.redis.server.CommandHandler.List;

import org.copy.redis.server.CommandHandler.List.Enity.ListDS;
import org.copy.redis.server.Encoder.RespEncoder;
import org.copy.redis.server.interfaces.CommandStrategy;

import java.util.LinkedList;
import java.util.ListIterator;

public class LInsertCommandStrategy extends ListDS implements CommandStrategy {
    @Override
    public String execute(String[] args) {
        if(args.length!=5)return RespEncoder.error("len!=5");
        String key=args[1];
        if(!args[2].equals("BEFORE")&&!args[2].equals("AFTER")){
            return RespEncoder.error("syntax error");
        }
        if(!map.containsKey(key)){return RespEncoder.integer(-1);}
        String pivot=args[3];
        String value=args[4];
        boolean before=args[2].equals("BEFORE");
        LinkedList<String> list = get(key);
        ListIterator<String> it = list.listIterator();
        while (it.hasNext()) {
            String current = it.next();
            if (current.equals(pivot)) {
                if (before) {
                    it.previous(); //
                }
                it.add(value);
                return RespEncoder.integer(list.size());
            }
        }
        return RespEncoder.integer(-1);
    }
}
