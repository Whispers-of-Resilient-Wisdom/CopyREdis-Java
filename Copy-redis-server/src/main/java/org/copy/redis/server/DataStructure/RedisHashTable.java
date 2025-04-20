package org.copy.redis.server.DataStructure;

import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;
@Setter
@Getter
public class RedisHashTable {
    private Set<String> set = new HashSet<>(10);
    public RedisHashTable() {



    }

    @Override
    public String toString() {
        return "RedisHashTable{" +
                "set=" + set +
                '}';
    }
}
