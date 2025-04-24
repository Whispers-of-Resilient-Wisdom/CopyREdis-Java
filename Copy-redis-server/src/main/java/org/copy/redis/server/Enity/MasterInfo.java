package org.copy.redis.server.Enity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MasterInfo {
    private String host;
    private int port;
    private String runId; // master 的唯一标识
    private ReplicationOffset offset;

    public MasterInfo(String host, int port, String runId) {
        this.host = host;
        this.port = port;
        this.runId = runId;
        this.offset = new ReplicationOffset(0, 0);
    }


}

