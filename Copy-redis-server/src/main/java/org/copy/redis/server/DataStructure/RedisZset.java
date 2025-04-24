package org.copy.redis.server.DataStructure;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListMap;
@Getter
@Setter
public class RedisZset {
    private Map<String, Double> dict;  // member -> score
    private ConcurrentSkipListMap<Double, Set<String>> skiplist;
    public RedisZset(){
        this.dict= new HashMap<>();
        this.skiplist=new ConcurrentSkipListMap<>();
    }

    @Override
    public String toString() {
        return "RedisZset{" +
                "dict=" + dict +
                ", skiplist=" + skiplist +
                '}';
    }
}
