package org.copy.redis.server.CommandHandler.Set.Enity;

import org.copy.redis.server.DataStructure.RedisHashTable;
import org.copy.redis.server.DataStructure.RedisObject;
import org.copy.redis.server.DataStructure.RedisServerDS;
import org.copy.redis.server.Enum.RedisEncoding;
import org.copy.redis.server.Enum.RedisType;

import java.util.HashSet;
import java.util.Set;

public class SetDS extends RedisServerDS {
     public void add(String key, Set<String > set) {//添加key
        RedisObject redisObject = new RedisObject();
        RedisHashTable redisHashTable = new RedisHashTable();
        redisHashTable.setSet(set);
        redisObject.setPtr(redisHashTable);
        redisObject.setType(RedisType.REDIS_SET.getType());
        redisObject.setEncoding(RedisEncoding.REDIS_ENCODING_LINKEDLIST.getType());
        map.put(key, redisObject);
    }

    public Set<String>get(String key){
        RedisObject redisObject = map.get(key);
        RedisHashTable ptr = (RedisHashTable) (redisObject.getPtr());
        return ptr.getSet();

    }
     public Set<String>union(String[]args,int j){

        Set<String> union = new HashSet<>();
        for(int i=j;i<args.length;i++) {
            Set<String> set = get(args[i]);
            union.addAll(set);

        }
        return union;


    }
    public Set<String>diff(String[]args,int j){

        Set<String> diff = new HashSet<>();
        for(int i=j;i<args.length;i++) {
            Set<String> set = get(args[i]);
            if(i==j)diff.addAll(set);
            else diff.removeAll(set);
        }
        return diff;
    }
     public Set<String> SInter(String []args){//这里的是
        Set<String> inter = new HashSet<>();
        boolean flag=false;
        for(int i=2;i<args.length;i++){//2是待处理集合
            if(!map.containsKey(args[i])) {
                flag=true;
                break;

            }
        }
        if(flag) return inter ;

        for(int i=2;i<args.length;i++) {

            Set<String> set = get(args[i]) ;
            if(i==2)inter.addAll(set);
            else inter.retainAll(set);
        }
        return inter;



    }

}
