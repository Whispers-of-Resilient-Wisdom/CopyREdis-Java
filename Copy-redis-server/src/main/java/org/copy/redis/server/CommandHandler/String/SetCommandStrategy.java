package org.copy.redis.server.CommandHandler.String;

import org.copy.redis.server.CommandHandler.String.Enity.StringDS;
import org.copy.redis.server.interfaces.CommandStrategy;
import org.copy.redis.server.DataStructure.SDS;
import org.copy.redis.server.Encoder.RespEncoder;

public class SetCommandStrategy extends StringDS implements CommandStrategy {
    static String errorSet="-ERR wrong number of arguments for 'set'";

    @Override
    public  String execute(String[]args) {
        if (args.length != 3) {
            return RespEncoder.error(errorSet);
        }
        String key = args[1];
        String value = args[2];
        add(key,new SDS(value));
        return RespEncoder.simpleString("OK");

    }
}
