package org.copy.redis.client.constant;

import lombok.Getter;


/**
 * 包含TTL,TYPE,EXISTS --读操作
 * DEL,RENAME，EXPIRE --写操作
 */

@Getter
public enum CommonConstant {
    TTL("TTL"),DEL("DEL"),
    RENAME("RENAME"),EXPIRE("EXPIRE"),
    EXISTS("EXITS"),TYPE("TYPE");
    private String constant;

    CommonConstant(String ttl) {
        constant = ttl;
    }
}
