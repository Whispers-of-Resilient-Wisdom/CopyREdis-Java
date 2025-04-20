package org.copy.redis.server.CommandHandler.String;

import org.copy.redis.server.CommandHandler.String.Enity.StringDS;
import org.copy.redis.server.interfaces.CommandStrategy;
import org.copy.redis.server.Encoder.RespEncoder;

public class MgetCommandStrategy extends StringDS implements CommandStrategy {
    @Override
    public String execute(String[] args) {
        if(args.length <2 ) {
            return RespEncoder.error("Mget len<2");
        }
        int len = args.length;
        String[] value = new String[len-1];
        for (int i = 1; i < args.length; i++) {
            StringBuilder s=get(args[i]);
            if(s == null) {
                value[i] = null;

            }
            else value[i] = s.toString();


        }

        return RespEncoder.array(value);
    }


}
