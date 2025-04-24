package org.copy.redis.server.Enity;

import lombok.Getter;
import lombok.Setter;
import org.copy.redis.server.Enum.ReplicationState;

import java.net.Socket;
@Getter
@Setter
public class SlaveInfo {
    private String id;//生成机子的id
    private Socket socket; // 可选，用于发送复制数据
    private ReplicationState state;
    private ReplicationOffset offset;

    public SlaveInfo(String id, Socket socket) {
        this.id = id;
        this.socket = socket;
        this.state = ReplicationState.CONNECT;
        this.offset = new ReplicationOffset(0, 0);
    }


}
