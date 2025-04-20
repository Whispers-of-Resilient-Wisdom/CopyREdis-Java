package org.copy.redis.server.CommandHandler.String;


import org.copy.redis.server.CommandHandler.String.Enity.StringDS;
import org.copy.redis.server.DataStructure.SDS;
import org.copy.redis.server.Encoder.RespEncoder;
import org.copy.redis.server.interfaces.CommandStrategy;

public class IncreByCommandStrategy extends StringDS implements CommandStrategy {
    @Override
    public String execute(String[] args) {
        if(args.length != 3) {
            return RespEncoder.error(" len!=3");
        }
        try {
            String key = args[1];
            String value = args[2];
            long i = Long.parseLong(value);
            boolean flag=map.containsKey(key);
            if(!flag) {
                SDS sds = new SDS(value);
                add(key, sds);}
            if (flag) {
                StringBuilder s = get(key);
                i += Long.parseLong(s.toString());
            }
            return RespEncoder.integer(i);
        }catch (Exception e) {

            return RespEncoder.integer(-1);//是指value不是合法整数

        }

    }

}

