package org.copy.redis.server.DataStructure;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RedisObject {
    private byte type;
    private long encoding;
    private Object ptr;

    public RedisObject() {
    }
}