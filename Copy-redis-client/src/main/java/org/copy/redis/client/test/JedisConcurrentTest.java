package org.copy.redis.client.test;

import redis.clients.jedis.Jedis;
import java.util.concurrent.*;


public class JedisConcurrentTest {
    private static final String REDIS_HOST = "localhost";
    private static final int REDIS_PORT = 6379;  // 你的模拟 Redis 服务端口
    private static final int THREAD_COUNT = 10;  // 模拟 10 个并发客户端
    private static final int REQUESTS_PER_CLIENT = 100;  // 每个客户端发送 100 个请求

    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(THREAD_COUNT);

        for (int i = 0; i < THREAD_COUNT; i++) {
            final int clientId = i;
            executor.submit(() -> {
                try (Jedis jedis = new Jedis(REDIS_HOST, REDIS_PORT)) {
                    for (int j = 0; j < REQUESTS_PER_CLIENT; j++) {
                        String key = "key_" + clientId + "_" + j;
                        String value = "value_" + j;

                        // 1. 发送 SET 命令
                        String setResponse = jedis.set(key, value);
                        System.out.println("Client " + clientId + " SET response: " + setResponse);

                        // 2. 发送 GET 命令
                        String getResponse = jedis.get(key);
                        System.out.println("Client " + clientId + " GET response: " + getResponse);


                    }
                } catch (Exception e) {
                    System.err.println("Client " + clientId + " error: " + e.getMessage());
                }
            });
        }

        executor.shutdown();
        try {
            executor.awaitTermination(1, TimeUnit.MINUTES);  // 等待所有线程完成
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

}