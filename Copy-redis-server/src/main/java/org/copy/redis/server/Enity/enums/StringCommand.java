package org.copy.redis.server.Enity.enums;

import lombok.Getter;

@Getter//主要用于后续的判断..也可以将后续constant
public enum StringCommand {
    SET("SET", -3),              // SET key value [EX seconds|PX milliseconds|EXAT timestamp|PXAT milliseconds-timestamp|KEEPTTL] [NX|XX] [GET]
    GET("GET", 2),               // GET key
    GETSET("GETSET", 3),         // GETSET key value
    SETNX("SETNX", 3),           // SETNX key value
    MSET("MSET", -3),            // MSET key value [key value ...]
    MGET("MGET", -2),            // MGET key [key ...]
    INCR("INCR", 2),             // INCR key
    INCRBY("INCRBY", 3),         // INCRBY key increment
    DECR("DECR", 2),             // DECR key
    DECRBY("DECRBY", 3),         // DECRBY key decrement
    APPEND("APPEND", 3),         // APPEND key value
    STRLEN("STRLEN", 2);         // STRLEN key

    private final String command;
    private final int len;

    StringCommand(String command, int len) {
        this.command = command;
        this.len = len;
    }

    public static boolean judgeString(String input) {
        for (StringCommand cmd : values()) {
            if (cmd.command.equalsIgnoreCase(input)) {
                return true;
            }
        }
        return false;
    }
}
