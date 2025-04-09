package org.copy.redis.server.Correspond;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class RedisServer {
    private static final int PORT = 6379;
    private static Map<String, String> store = new HashMap<>();

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(PORT);
        System.out.println("Redis Server started on port " + PORT);

        while (true) {
            Socket clientSocket = serverSocket.accept();
            new Thread(new ClientHandler(clientSocket)).start();
        }
    }

    static class ClientHandler implements Runnable {
        private Socket socket;
        private BufferedReader reader;
        private BufferedWriter writer;

        public ClientHandler(Socket socket) throws IOException {
            this.socket = socket;
            this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        }

        @Override
        public void run() {
            try {
                while (true) {
                    String line = reader.readLine();
                    if (line == null) break;

                    if (line.startsWith("*")) { // 解析 RESP 命令
                        int argsCount = Integer.parseInt(line.substring(1));
                        String[] args = new String[argsCount];

                        for (int i = 0; i < argsCount; i++) {
                            reader.readLine(); // 读取 $length
                            args[i] = reader.readLine(); // 读取参数
                        }

                        processCommand(args);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        private void processCommand(String[] args) throws IOException {
            String command = args[0].toUpperCase();

            if ("SET".equals(command)) {
                store.put(args[1], args[2]);
                writer.write("+OK\r\n");
            } else if ("GET".equals(command)) {
                String value = store.get(args[1]);
                if (value == null) {
                    writer.write("$-1\r\n"); // RESP 格式：空值返回 $-1
                } else {
                    writer.write("$" + value.length() + "\r\n" + value + "\r\n");
                }
            }
            else  if("DEL".equals(command)) {
                

            }
            else {
                writer.write("-ERR Unknown command\r\n");
            }
            writer.flush();
        }
    }
}
