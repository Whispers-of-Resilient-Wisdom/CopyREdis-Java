package org.copy.redis.server.CommandHandler.Hash;


import org.copy.redis.server.CommandHandler.Hash.Enity.HashDS;
import org.copy.redis.server.interfaces.CommandStrategy;
import org.copy.redis.server.DataStructure.RedisDict;
import org.copy.redis.server.Encoder.RespEncoder;
import java.util.Map;
//hset key field value field value
public class HSetCommandStrategy extends HashDS implements CommandStrategy {
    @Override
    public String execute(String[] args) {
        if (args.length < 4 || args.length % 2 == 1) {
            return RespEncoder.error(" hset len<3||len%2==1");
        }
        String key=args[1];
        long count=0;
        if(!map.containsKey(key)){
            RedisDict redisDict = new RedisDict();
            Map<String, String> dict = redisDict.getDict();
            for(int i=2;i<args.length;i=i+2){
                dict.put(args[i],args[i+1]);


            }

            add(key,redisDict);
            count= (args.length-2)/2;//错了，由于length有关于field,value
        }else{
            Map<String, String> dict = get(key);
            for(int i=2;i<args.length;i=i+2){
                boolean flag= dict.containsKey(args[i]);
                if(!flag)
                {count++;
                dict.put(args[i],args[i+1]);}


            }



        }


        return RespEncoder.integer(count);

    }
}

