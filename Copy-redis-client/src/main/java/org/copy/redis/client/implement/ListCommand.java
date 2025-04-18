package org.copy.redis.client.implement;

import org.copy.redis.client.interfaces.abstractCommand;

import java.io.IOException;
import java.util.List;

public class ListCommand<V> extends abstractCommand<V>{

    public ListCommand(String host, int port, Class<V> type) throws IOException {
        super(host, port, type);
    }
    @Override
    //todo 将list，转变为String value,额外处理
    public void LPUSH(String key, List< V > value) throws IOException {
        sendCommand("LPUSH", key);

    }
}
