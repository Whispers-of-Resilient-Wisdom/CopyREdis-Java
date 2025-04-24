package org.copy.redis.server.Enity.enums;

import lombok.Getter;

@Getter
public enum CommonCommand {
    DEL("DEL", -2),             // DEL key [key ...]
    EXISTS("EXISTS", -2),       // EXISTS key [key ...]
    EXPIRE("EXPIRE", 3),        // EXPIRE key seconds
    EXPIREAT("EXPIREAT", 3),    // EXPIREAT key timestamp
    PERSIST("PERSIST", 2),      // PERSIST key
    TTL("TTL", 2),              // TTL key
    PTTL("PTTL", 2),            // PTTL key
    TYPE("TYPE", 2),            // TYPE key
    RENAME("RENAME", 3),        // RENAME key newkey
    RENAMENX("RENAMENX", 3),    // RENAMENX key newkey
    KEYS("KEYS", 2),            // KEYS pattern
    RANDOMKEY("RANDOMKEY", 1);  // RANDOMKEY

    private final String command;
    private final int len;

    CommonCommand(String command, int len) {
        this.command = command;
        this.len = len;
    }

    public static boolean judgeCommon(String input) {
        for (CommonCommand cmd : values()) {
            if (cmd.command.equalsIgnoreCase(input)) {
                return true;
            }
        }
        return false;
    }
}
