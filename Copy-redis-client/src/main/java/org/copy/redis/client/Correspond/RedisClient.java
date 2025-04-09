package org.copy.redis.client.Correspond;
import java.io.*;
import java.net.Socket;

public class RedisClient {
    private Socket socket;
    private BufferedReader reader;
    private BufferedWriter writer;

    public RedisClient(String host, int port) throws IOException {
        socket = new Socket(host, port);
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
    }

    // 发送 RESP 格式的命令
    private void sendCommand(String command, String... args) throws IOException {
        writer.write("*" + (args.length + 1) + "\r\n");
        writer.write("$" + command.length() + "\r\n" + command + "\r\n");
        for (String arg : args) {
            writer.write("$" + arg.length() + "\r\n" + arg + "\r\n");
        }
        writer.flush();
    }

    private String readResponse() throws IOException {
        String line = reader.readLine();

        if (line.startsWith("+") || line.startsWith("-")) {
            return line.substring(1); // 直接返回 OK 或错误信息
        } else if (line.startsWith("$")) {
            int length = Integer.parseInt(line.substring(1)); // 解析长度
            if (length == -1) return null; // 返回 null 表示 key 不存在
            return reader.readLine(); // 读取实际数据
        }

        return line; // 其他情况（暂不支持）
    }


    public void set(String key, String value) throws IOException {
        sendCommand("SET", key, value);
        System.out.println("Server: " + readResponse());
    }

    public String get(String key) throws IOException {
        sendCommand("GET", key);
        return readResponse();
    }

    public void close() throws IOException {
        socket.close();
    }

    public static void main(String[] args) throws IOException {
        RedisClient client = new RedisClient("localhost", 6379);
        client.set("word", "");
        System.out.println("GET hello: " + client.get("word"));
        client.close();
    }
}

