package org.copy.redis.client.interfaces;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;

public abstract class  abstractCommand<V> implements CommandInterface<V>{
    public Class<V> type;
    private Socket socket;
    static   private BufferedReader reader;
    static private BufferedWriter writer;
    public abstractCommand(java.lang.String host, int port,Class<V> type) throws IOException {
        socket = new Socket(host, port);
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        this.type=type;
    }
    public void sendCommand(String command, String... args) throws IOException {
        writer.write("*" + (args.length + 1) + "\r\n");
        writer.write("$" + command.length() + "\r\n" + command + "\r\n");
        for (String arg : args) {
            writer.write("$" + arg.length() + "\r\n" + arg + "\r\n");
        }
        System.out.println(writer);
        writer.flush();
    }
    public java.lang.String readResponse() throws IOException {
        java.lang.String line = reader.readLine();

        if (line.startsWith("+") || line.startsWith("-")) {
            return line.substring(1); // 直接返回 OK 或错误信息
        } else if (line.startsWith("$")) {
            int length = Integer.parseInt(line.substring(1)); // 解析长度
            if (length == -1) return null; // 返回 null 表示 key 不存在
            return reader.readLine(); // 读取实际数据
        }

        return line; // 其他情况（暂不支持）
    }



    @Override
    public String build(String command,String... args){
        return null;
    }
    @Override
    public void set(String key, V value) throws IOException {}

    @Override
    public V get(String key) throws IOException { return null; }

    @Override
    public void close() throws IOException {
        socket.close();
    }

    @Override
    public void del(String key) throws IOException {}

    @Override
    public void expire(String key, long seconds) throws IOException {}

    @Override
    public boolean exists(String key) throws IOException {
        return true;
    }

    @Override
    public void TTL(String key) throws IOException {}

    @Override
    public void PERSIST(String key) throws IOException {}

    @Override
    public int KEYS(String key) throws IOException { return 0; }

    @Override
    public void RENAME(String oldKey, String newKey) throws IOException {}

    @Override
    public void INCR(String key) throws IOException {}

    @Override
    public void APPEND(String key, String value) throws IOException {}

    @Override
    public void DECR(String key) throws IOException {}

    @Override
    public void MSET(HashMap<String, V> value) throws IOException {}

    @Override
    public void STRLEN(String key) throws IOException {}

    @Override
    public void LPUSH(String key, List<V> value) throws IOException {}

    @Override
    public void RPUSH(String key, List<String> value) throws IOException {}

    @Override
    public V LPOP(String key) throws IOException { return null; }

    @Override
    public void RPOP(String key) throws IOException {}

    @Override
    public List<V> LRANGE(String key, int start, int stop) throws IOException { return null; }

    @Override
    public int LLEN(String key) throws IOException { return 0; }

    @Override
    public boolean LREM(String key, int count, int value) throws IOException { return false; }

    @Override
    public boolean INDEX(String key, int index) throws IOException { return false; }

    @Override
    public boolean INDEX(String key, List<String> member) throws IOException { return false; }

    @Override
    public void SREM(String key, String member) throws IOException {}

    @Override
    public void SMEMBERS(String key) throws IOException {}

    @Override
    public void SISMEMBER(String key, String member) {}

    @Override
    public void SCARD(String key) throws IOException {}

    @Override
    public void SRANDMEMBER(String key, int count) throws IOException {}

    @Override
    public void SDIFF(String key1, String key2) throws IOException {}

    @Override
    public void SINTER(String key1, String key2) throws IOException {}

    @Override
    public void SUNION(String key1, String key2) throws IOException {}

}
