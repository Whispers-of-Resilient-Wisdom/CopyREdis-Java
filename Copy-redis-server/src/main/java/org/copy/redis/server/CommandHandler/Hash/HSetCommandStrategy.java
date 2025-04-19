package org.copy.redis.server.CommandHandler.Hash;


import org.copy.redis.server.interfaces.CommandStrategy;
import org.copy.redis.server.DataStructure.RedisDict;
import org.copy.redis.server.DataStructure.RedisObject;
import org.copy.redis.server.DataStructure.RedisServerDS;
import org.copy.redis.server.Encoder.RespEncoder;
import org.copy.redis.server.Enum.RedisEncoding;
import org.copy.redis.server.Enum.RedisType;

import java.util.Map;
//hset key field value field value
public class HSetCommandStrategy extends RedisServerDS implements CommandStrategy {
    @Override
    public String execute(String[] args) {
        if(args.length <4||args.length%2==1) {
            return RespEncoder.error(" hset len<3||len%2==1");
        }
        String key=args[1];
        long count=0;
        if(!map.containsKey(key)){
            RedisObject redisObject = new RedisObject();
            RedisDict redisDict = new RedisDict();
            Map<String, String> dict = redisDict.getDict();
            for(int i=2;i<args.length;i=i+2){
                dict.put(args[i],args[i+1]);


            }
            redisObject.setPtr(redisDict);//set hash
            redisObject.setType(RedisType.REDIS_HASH.getType());
            redisObject.setEncoding(RedisEncoding.REDIS_ENCODING_HT.getType());
            map.put(key,redisObject);
            count= (args.length-2)/2;//错了，由于length有关于field,value
        }else{
            RedisObject redisObject=(RedisObject)( map.get(key));
            RedisDict ptr = (RedisDict) (redisObject.getPtr());
            Map<String, String> dict = ptr.getDict();
            for(int i=2;i<args.length;i=i+2){
                boolean flag= dict.containsKey(args[i]);
                if(!flag) count++;
                dict.put(args[i],args[i+1]);
                System.out.println("hset"+args[i]+":"+args[i+1]);

            }
            map.put(key,redisObject);//String



        }


        return RespEncoder.integer(count);

    }
}

