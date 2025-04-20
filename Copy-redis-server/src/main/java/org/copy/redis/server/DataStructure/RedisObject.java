

package org.copy.redis.server.DataStructure;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RedisObject {
    private byte type;
    private long encoding;
    private Object ptr;

    public RedisObject() {
    }

    public String toString() {
        return "RedisObject{encoding=" + this.encoding + ", type=" + this.type + ", ptr=" + this.ptr + '}';
    }


}
