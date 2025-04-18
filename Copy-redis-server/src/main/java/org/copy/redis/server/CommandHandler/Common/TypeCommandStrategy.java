package org.copy.redis.server.CommandHandler.Common;


import org.copy.redis.server.interfaces.CommandStrategy;
import org.copy.redis.server.DataStructure.RedisObject;
import org.copy.redis.server.DataStructure.RedisServerDS;
import org.copy.redis.server.Encoder.RespEncoder;


public class TypeCommandStrategy extends RedisServerDS implements CommandStrategy {
    public static String Return_none="none";
    static String Return_String="string";
    static String Return_List="list";
    static String Return_Set="set";
    static String Return_Hash="hash";
    static String Return_ZSet="zset";
    @Override
    public  String execute(String[]args) {
        String key=args[1];
        String f=Return_none;
        boolean  exists= map.containsKey(key);
        if(exists) {//根据
            RedisObject redisObject = map.get(key);

            byte type = redisObject.getType();
            f=judgeType(type);



        }

        return RespEncoder.simpleString(f);

    }
    static String judgeType(byte type){
        String f="";
        switch (type) {
            case 1:
                f = Return_String;
                break;
            case 2:
                f = Return_List;
                break;
            case 3:
                f = Return_Hash;
                break;

            case 4:
                f=Return_Set;
                break;
            case 5:
                f=Return_ZSet;
                break;
        }



        return f;
    }
}
