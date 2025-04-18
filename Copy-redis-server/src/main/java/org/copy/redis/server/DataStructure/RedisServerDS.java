package org.copy.redis.server.DataStructure;
import java.util.HashMap;

public class RedisServerDS {
    HashMap<SDS, RedisObject> map;
    HashMap<SDS, Long> expire;

    public RedisServerDS() {
    }
}
