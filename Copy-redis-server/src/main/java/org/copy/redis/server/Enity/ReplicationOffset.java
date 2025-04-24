package org.copy.redis.server.Enity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReplicationOffset {
    private long masterOffset;   // master 端的偏移量
    private long slaveOffset;    // slave 当前处理到的偏移量

    public ReplicationOffset(long masterOffset, long slaveOffset) {
        this.masterOffset = masterOffset;
        this.slaveOffset = slaveOffset;
    }


}

