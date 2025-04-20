package org.copy.redis.server.CommandHandler.Set;

import org.copy.redis.server.CommandHandler.Set.Enity.SetDS;
import org.copy.redis.server.Encoder.RespEncoder;
import org.copy.redis.server.interfaces.CommandStrategy;

import java.util.Set;

public class SUnionCommandStrategy extends SetDS implements CommandStrategy {
    @Override
    public String execute(String[] args) {
        if(args.length < 2) {
            return RespEncoder.error("len<2");
        }
        Set<String> union = union(args, 1);
        return RespEncoder.array(union.toArray(new String[0]));
    }
}
