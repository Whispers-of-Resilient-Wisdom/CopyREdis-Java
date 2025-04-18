package org.copy.redis.client.implement;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.copy.redis.client.interfaces.abstractCommand;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;

public class StringCommand<V> extends abstractCommand<V> {
    static ObjectMapper mapper = new ObjectMapper();

    public StringCommand(String host, int port, Class<V> type) throws IOException {
        super(host, port, type);
    }
    @Override
    public void set(String key, V value) throws IOException {
        String json =  mapper.writeValueAsString(value);
        System.out.println(json);
        sendCommand("SET", key, json);

    }

    @Override
    public V get(String key) throws IOException {//转换类型
        try {
            sendCommand("GET", key);

            String json = readResponse();
            System.out.println(json);
            return null;
        } catch (JsonProcessingException e) {
            throw new IOException("Failed to parse JSON response for key: " + key, e);
        }



    }
    @Override
    public void  INCR(String key) throws IOException {
        sendCommand("INCR", key);


    }
    @Override
    public void  DECR(String key) throws IOException {
        sendCommand("DECR", key);


    }
    @Override
    //todo
    public void   MSET (HashMap<String, V> key) throws IOException {
        sendCommand(" MSET", key.toString());


    }

    public static void main(String[] args)throws Exception {
        JedisPool pool = new JedisPool("localhost", 6379);
        try (Jedis jedis = pool.getResource()) {
            jedis.set("key", "value");
            System.out.println(jedis.get("key")); // 不会频繁断连
        }


    }
}
