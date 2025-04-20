package org.copy.redis.server.CommandHandler.Hash;

import org.copy.redis.server.CommandHandler.Hash.Enity.HashDS;
import org.copy.redis.server.interfaces.CommandStrategy;
import org.copy.redis.server.DataStructure.RedisDict;
import org.copy.redis.server.Encoder.RespEncoder;


import java.util.Map;
//hincrby key field incre
//    # 返回错误: (error) ERR hash value is not an integer
//   increment is not  a value          # 返回: (error) ERR value is not an integer or out of range
public class HincrbyCommandStrategy extends HashDS implements CommandStrategy {
    @Override
    public String execute(String[] args) {
        if (args.length != 4) {
            return RespEncoder.error("hincrby length!=4");
        }
        String key=args[1];
        String field=args[2];
        long count;
        try{
            count = Long.parseLong(args[3]);

        }
        catch(NumberFormatException e){
            return RespEncoder.error(" incre is not number");

        }
        long number=0;
        if(!map.containsKey(key)){

            RedisDict redisDict = new RedisDict();
            Map<String, String> dict = redisDict.getDict();
            dict.put(field,Long.toString(count));
            add(key,redisDict);
        }else{
            Map<String, String> dict = get(key);
            boolean flag = dict.containsKey(field);
            if(flag){
                String s = dict.get(field);
                try {
                    int i = Integer.parseInt(s);
                    number = i+count;
                    dict.put(field,Long.toString(number));
                }
                catch (NumberFormatException e) {
                    return RespEncoder.error("value is not number");

                }
            }
            else{
                dict.put(field,Long.toString(count));


            }


        }

        return RespEncoder.integer(number);

    }
}
