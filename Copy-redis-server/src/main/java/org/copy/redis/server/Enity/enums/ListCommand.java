package org.copy.redis.server.Enity.enums;

import lombok.Getter;
@Getter
public enum ListCommand {
    LPUSH("LPUSH", -3),          // LPUSH key element [element ...]
    RPUSH("RPUSH", -3),          // RPUSH key element [element ...]
    LPOP("LPOP", -2),            // LPOP key [count]
    RPOP("RPOP", -2),            // RPOP key [count]
    LRANGE("LRANGE", 4),         // LRANGE key start stop
    LINDEX("LINDEX", 3),         // LINDEX key index
    LSET("LSET", 4),             // LSET key index element
    LREM("LREM", 4),             // LREM key count element
    LLEN("LLEN", 2),             // LLEN key
    LTRIM("LTRIM", 4),           // LTRIM key start stop
    RPOPLPUSH("RPOPLPUSH", 3),   // RPOPLPUSH source destination
    BLPOP("BLPOP", -2),          // BLPOP key [key ...] timeout
    BRPOP("BRPOP", -2),          // BRPOP key [key ...] timeout
    BRPOPLPUSH("BRPOPLPUSH", 4); // BRPOPLPUSH source destination timeout

    private final String command;
    private final int len;

    ListCommand(String command, int len) {
        this.command = command;
        this.len = len;
    }

    public static boolean judgeList(String input) {
        for (ListCommand cmd : values()) {
            if (cmd.command.equalsIgnoreCase(input)) {
                return true;
            }
        }
        return false;
    }
}