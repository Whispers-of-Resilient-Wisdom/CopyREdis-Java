package org.copy.redis.server.Enity.enums;

import lombok.Getter;

@Getter
public enum HashCommand {
    HSET("HSET", -4),          // HSET key field value [field value ...]
    HGET("HGET", 3),           // HGET key field
    HDEL("HDEL", -3),          // HDEL key field [field ...]
    HMSET("HMSET", -4),        // HMSET key field value [field value ...] (Deprecated)
    HMGET("HMGET", -3),        // HMGET key field [field ...]
    HGETALL("HGETALL", 2),     // HGETALL key
    HKEYS("HKEYS", 2),         // HKEYS key
    HVALS("HVALS", 2),         // HVALS key
    HLEN("HLEN", 2),           // HLEN key
    HEXISTS("HEXISTS", 3),     // HEXISTS key field
    HINCRBY("HINCRBY", 4),     // HINCRBY key field increment
    HINCRBYFLOAT("HINCRBYFLOAT", 4); // HINCRBYFLOAT key field increment

    private final String command;
    private final int len;

    HashCommand(String command, int len) {
        this.command = command;
        this.len = len;
    }

    public static boolean judeHash(String input) {
        for (HashCommand cmd : values()) {
            if (cmd.command.equalsIgnoreCase(input)) {
                return true;
            }
        }
        return false;
    }
}