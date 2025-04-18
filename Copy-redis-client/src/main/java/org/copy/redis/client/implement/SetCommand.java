package org.copy.redis.client.implement;

import org.copy.redis.client.interfaces.abstractCommand;

import java.io.IOException;

public class SetCommand<V> extends abstractCommand<V> {

    public SetCommand(String host, int port, Class<V> type) throws IOException {
        super(host, port, type);
    }
}
