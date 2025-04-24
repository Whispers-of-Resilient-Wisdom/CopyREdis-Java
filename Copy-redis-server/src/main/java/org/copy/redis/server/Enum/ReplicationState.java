package org.copy.redis.server.Enum;

public enum ReplicationState {
    CONNECT,            // 正在连接
    CONNECTING,         // 已发送 PSYNC，等待响应
    SYNCING,            // 正在进行同步
    SYNCED,             // 完成同步
    DISCONNECTED        // 断开连接
}

