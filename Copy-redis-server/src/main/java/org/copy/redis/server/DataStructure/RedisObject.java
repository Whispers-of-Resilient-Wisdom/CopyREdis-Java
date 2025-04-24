

package org.copy.redis.server.DataStructure;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RedisObject {
    private byte type;
    private byte encoding;
    private Object ptr;

    public RedisObject() {
    }

    public RedisObject(byte type,byte encoding, Object ptr) {
        this.encoding = encoding;
        this.ptr = ptr;
        this.type = type;
    }

    public String toString() {
        return "RedisObject{encoding=" + this.encoding + ", type=" + this.type + ", ptr=" + this.ptr + '}';
    }



}
