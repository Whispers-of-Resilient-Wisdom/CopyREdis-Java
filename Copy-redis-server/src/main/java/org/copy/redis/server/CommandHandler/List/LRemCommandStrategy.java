package org.copy.redis.server.CommandHandler.List;

import org.copy.redis.server.CommandHandler.List.Enity.ListDS;
import org.copy.redis.server.Encoder.RespEncoder;
import org.copy.redis.server.interfaces.CommandStrategy;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;

public class LRemCommandStrategy extends ListDS implements CommandStrategy {
    @Override
    public String execute(String[] args) {
        if(args.length!=4)return RespEncoder.error("len!=4");
        long count;
        try{
            count=Long.parseLong(args[2]);

        }
        catch (NumberFormatException e){
            return RespEncoder.error("ERR value is not an integer or out of range");

        }
        String key=args[1];
        String value=args[3];
        if(!map.containsKey(key)){
            return RespEncoder.integer(0);

        }
        LinkedList<String> list = get(key);
        if (list==null|| list.isEmpty()) return RespEncoder.integer(0);

        int removed = 0;

        if (count == 0) {
            Iterator<String> it = list.iterator();
            while (it.hasNext()) {
                if (it.next().equals(value)) {
                    it.remove();
                    removed++;
                }
            }
        }
        else if (count > 0) {
            ListIterator<String> it = list.listIterator();
            while (it.hasNext() && removed < count) {
                if (it.next().equals(value)) {
                    it.remove();
                    removed++;
                }
            }
        }
        else {
            ListIterator<String> it = list.listIterator(list.size());
            while (it.hasPrevious() && removed < -count) {
                if (it.previous().equals(value)) {
                    it.remove();
                    removed++;
                }
            }
        }

        return RespEncoder.integer(removed);
    }
}
