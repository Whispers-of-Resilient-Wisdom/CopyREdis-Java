package org.copy.redis.server.Enity.enums;

import lombok.Getter;

@Getter
public enum WriterCommand {
    // String
    SET("SET"),
    SETNX("SETNX"),
    GETSET("GETSET"),
    MSET("MSET"),
    INCR("INCR"),
    INCRBY("INCRBY"),
    DECR("DECR"),
    DECRBY("DECRBY"),
    APPEND("APPEND"),

    // List
    LPUSH("LPUSH"),
    RPUSH("RPUSH"),
    LSET("LSET"),
    LREM("LREM"),
    LTRIM("LTRIM"),
    RPOPLPUSH("RPOPLPUSH"),

    // Hash
    HSET("HSET"),
    HMSET("HMSET"), // 虽然被弃用，仍支持
    HDEL("HDEL"),
    HINCRBY("HINCRBY"),
    HINCRBYFLOAT("HINCRBYFLOAT"),

    // Set
    SADD("SADD"),
    SREM("SREM"),
    SMOVE("SMOVE"),

    // ZSet
    ZADD("ZADD"),
    ZREM("ZREM"),
    ZINCRBY("ZINCRBY"),
    ZREMRANGEBYRANK("ZREMRANGEBYRANK"),
    ZREMRANGEBYSCORE("ZREMRANGEBYSCORE"),

    // 通用
    DEL("DEL");

    private final String commandName;

    WriterCommand(String commandName) {
        this.commandName = commandName;
    }

    public static boolean isWriter(String command) {
        for (WriterCommand cmd : values()) {
            if (cmd.commandName.equalsIgnoreCase(command)) {
                return true;
            }
        }
        return false;
    }
}
