package org.copy.redis.server.CommandHandler.Set;

import org.copy.redis.server.CommandHandler.Set.Enity.SetDS;
import org.copy.redis.server.Encoder.RespEncoder;
import org.copy.redis.server.interfaces.CommandStrategy;

import java.util.Set;

public class SUnionStoreCommandStrategy  extends SetDS implements CommandStrategy {
    @Override
    public String execute(String[] args) {
        if (args.length < 3) {

            return RespEncoder.error("len<3");

        }
        String key = args[1];//代存储
        long count;
        Set<String> strings;
        if (!map.containsKey(args[1])) {
            strings = union(args,2);
            add(key,strings);
        } else {
            Set<String> set = get(key);
            strings = union(args,2);
            set.clear();
            set.addAll(strings);
        }
        count=strings.size();
        return RespEncoder.integer(count);
    }
}
