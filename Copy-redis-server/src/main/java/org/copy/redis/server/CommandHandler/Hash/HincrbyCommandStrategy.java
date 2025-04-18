package org.copy.redis.server.CommandHandler.Hash;

import org.copy.redis.server.interfaces.CommandStrategy;
import org.copy.redis.server.DataStructure.RedisDict;
import org.copy.redis.server.DataStructure.RedisObject;
import org.copy.redis.server.DataStructure.RedisServerDS;
import org.copy.redis.server.Encoder.RespEncoder;
import org.copy.redis.server.Enum.RedisEncoding;
import org.copy.redis.server.Enum.RedisType;

import java.util.Map;
//hincrby key field incre
//    # 返回错误: (error) ERR hash value is not an integer
//   increment is not  a value          # 返回: (error) ERR value is not an integer or out of range
public class HincrbyCommandStrategy extends RedisServerDS implements CommandStrategy {
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
            RedisObject redisObject = new RedisObject();
            RedisDict redisDict = new RedisDict();
            Map<String, String> dict = redisDict.getDict();
            dict.put(field,Long.toString(count));
            redisObject.setPtr(new RedisDict());//set hash
            redisObject.setType(RedisType.REDIS_HASH.getType());
            redisObject.setEncoding(RedisEncoding.REDIS_ENCODING_HT.getType());
            map.put(key,redisObject);
        }else{
            RedisObject redisObject= map.get(key);
            RedisDict ptr = (RedisDict) (redisObject.getPtr());
            Map<String, String> dict = ptr.getDict();
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
