package org.copy.redis.server.Enity.enums;

import lombok.Getter;
@Getter
public enum SetCommand {
    SADD("SADD", -3),              // SADD key member [member ...]
    SREM("SREM", -3),              // SREM key member [member ...]
    SISMEMBER("SISMEMBER", 3),     // SISMEMBER key member
    SCARD("SCARD", 2),             // SCARD key
    SMEMBERS("SMEMBERS", 2),       // SMEMBERS key
    SPOP("SPOP", -2),              // SPOP key [count]
    SRANDMEMBER("SRANDMEMBER", -2),// SRANDMEMBER key [count]
    SMOVE("SMOVE", 4),             // SMOVE source destination member
    SDIFF("SDIFF", -2),            // SDIFF key [key ...]
    SDIFFSTORE("SDIFFSTORE", -3),  // SDIFFSTORE destination key [key ...]
    SINTER("SINTER", -2),          // SINTER key [key ...]
    SINTERSTORE("SINTERSTORE", -3),// SINTERSTORE destination key [key ...]
    SUNION("SUNION", -2),          // SUNION key [key ...]
    SUNIONSTORE("SUNIONSTORE", -3);// SUNIONSTORE destination key [key ...]

    private final String command;
    private final int len;

    SetCommand(String command, int len) {
        this.command = command;
        this.len = len;
    }

    public static boolean judgeSet(String input) {
        for (SetCommand cmd : values()) {
            if (cmd.command.equalsIgnoreCase(input)) {
                return true;
            }
        }
        return false;
    }
}
