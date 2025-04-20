package org.copy.redis.server.CommandHandler.String;

import org.copy.redis.server.CommandHandler.String.Enity.StringDS;
import org.copy.redis.server.interfaces.CommandStrategy;
import org.copy.redis.server.DataStructure.SDS;
import org.copy.redis.server.Encoder.RespEncoder;


public class MSetCommandStrategy  extends StringDS implements CommandStrategy {
    @Override
    public String execute(String[] args) {
        int len = args.length;
        if(len<2||len %2==0)return RespEncoder.error("len<2||len%2==0");
        for (int i = 1; i < len; i = i + 2) {
            add(args[i], new SDS(args[i]));

        }
        return RespEncoder.simpleString("OK");
    }



}