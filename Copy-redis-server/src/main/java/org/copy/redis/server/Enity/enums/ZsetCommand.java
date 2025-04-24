package org.copy.redis.server.Enity.enums;

import lombok.Getter;
@Getter
public enum ZsetCommand {
    ZADD("ZADD", -4),                  // ZADD key [NX|XX] [GT|LT] [CH] [INCR] score member [score member ...]
    ZREM("ZREM", -3),                  // ZREM key member [member ...]
    ZINCRBY("ZINCRBY", 4),             // ZINCRBY key increment member
    ZRANK("ZRANK", 3),                 // ZRANK key member
    ZREVRANK("ZREVRANK", 3),           // ZREVRANK key member
    ZSCORE("ZSCORE", 3),               // ZSCORE key member
    ZREMRANGEBYRANK("ZREMRANGEBYRANK", 4), // ZREMRANGEBYRANK key start stop
    ZREMRANGEBYSCORE("ZREMRANGEBYSCORE", -3), // ZREMRANGEBYSCORE key min max
    ZRANGE("ZRANGE", 4),              // ZRANGE key start stop [WITHSCORES]
    ZREVRANGE("ZREVRANGE", 4),        // ZREVRANGE key start stop [WITHSCORES]
    ZRANGEBYSCORE("ZRANGEBYSCORE", -3), // ZRANGEBYSCORE key min max [WITHSCORES] [LIMIT offset count]
    ZREVRANGEBYSCORE("ZREVRANGEBYSCORE", -3), // ZREVRANGEBYSCORE key max min [WITHSCORES] [LIMIT offset count]
    ZCOUNT("ZCOUNT", 4),               // ZCOUNT key min max
    ZLEXCOUNT("ZLEXCOUNT", 4),         // ZLEXCOUNT key min max
    ZRANGEBYLEX("ZRANGEBYLEX", -3),    // ZRANGEBYLEX key min max [LIMIT offset count]
    ZREVRANGEBYLEX("ZREVRANGEBYLEX", -3), // ZREVRANGEBYLEX key max min [LIMIT offset count]
    ZUNION("ZUNION", -3),              // ZUNION numkeys key [key ...] [WEIGHTS weight [weight ...]] [AGGREGATE SUM|MIN|MAX] [WITHSCORES]
    ZUNIONSTORE("ZUNIONSTORE", -3),    // ZUNIONSTORE destination numkeys key [key ...] [WEIGHTS weight [weight ...]] [AGGREGATE SUM|MIN|MAX]
    ZINTER("ZINTER", -3),              // ZINTER numkeys key [key ...] [WEIGHTS weight [weight ...]] [AGGREGATE SUM|MIN|MAX] [WITHSCORES]
    ZINTERSTORE("ZINTERSTORE", -4);    // ZINTERSTORE destination numkeys key [key ...] [WEIGHTS weight [weight ...]] [AGGREGATE SUM|MIN|MAX]

    private final String command;
    private final int len;

    ZsetCommand(String command, int len) {
        this.command = command;
        this.len = len;
    }

    public static boolean judgeZset(String input) {
        for (ZsetCommand cmd : values()) {
            if (cmd.command.equalsIgnoreCase(input)) {
                return true;
            }
        }
        return false;
    }
}