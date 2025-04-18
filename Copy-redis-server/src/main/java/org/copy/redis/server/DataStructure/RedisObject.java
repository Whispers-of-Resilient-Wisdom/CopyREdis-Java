

package org.copy.redis.server.DataStructure;

public class RedisObject {
    private byte type;
    private long encoding;
    private Object ptr;

    public RedisObject() {
    }

    public String toString() {
        return "RedisObject{encoding=" + this.encoding + ", type=" + this.type + ", ptr=" + this.ptr + '}';
    }

    public byte getType() {
        return this.type;
    }

    public long getEncoding() {
        return this.encoding;
    }

    public Object getPtr() {
        return this.ptr;
    }

    public void setType(byte type) {
        this.type = type;
    }

    public void setEncoding(long encoding) {
        this.encoding = encoding;
    }

    public void setPtr(Object ptr) {
        this.ptr = ptr;
    }
}
