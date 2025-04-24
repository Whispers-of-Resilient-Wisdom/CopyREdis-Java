package org.copy.redis.client.test;

import org.junit.jupiter.api.*;
import redis.clients.jedis.Jedis;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

public class RedisCommandTest {
    private static Jedis jedis;
    private static final String TEST_PREFIX = "test:";

    @BeforeAll
    static void setup() {
        jedis = new Jedis("localhost", 6379); // 连接你的模拟Redis
//        jedis.flushAll();
    }

    @AfterEach
    void cleanup() {
        // 删除当前测试的key
        jedis.del(TEST_PREFIX + "*");
    }

    @AfterAll
    static void teardown() {
        jedis.close();
    }



    @Test
    void testMalformedCommand() {
        // 直接发送不符合RESP协议的请求
        try (Socket socket = new Socket("localhost", 6379);
             OutputStream out = socket.getOutputStream()) {
            out.write("BAD COMMAND\r\n".getBytes());
            out.flush();
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String response = in.readLine();
            assertTrue(response.startsWith("-ERR")); // 应返回错误响应
        } catch (IOException e) {
            fail("Connection failed: " + e.getMessage());
        }
    }
    @Test
    void testHDEL() {
        // 准备数据
        assertEquals(1,jedis.hset(TEST_PREFIX + "user", "name", "Alice"));
        assertEquals(1,jedis.hset(TEST_PREFIX + "age", "name", "30"));


        // 删除单个字段
        assertEquals(1, jedis.hdel(TEST_PREFIX + "user", "name"));
        assertNull(jedis.hget(TEST_PREFIX + "user", "name"));

        // 删除不存在的字段
        assertEquals(0, jedis.hdel(TEST_PREFIX + "user", "salary"));

        // 批量删除
        jedis.hset(TEST_PREFIX + "product", "id", "1001");
        jedis.hset(TEST_PREFIX + "product", "price", "99.9");
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        assertEquals(2, jedis.hdel(TEST_PREFIX + "product", "id", "price"));
    }
    @Test //出现过问题，命令的字符串打错了
    void testEXISTS() {
        jedis.set(TEST_PREFIX + "key", "value");
        assertEquals(true, jedis.exists(TEST_PREFIX + "key"));
        assertEquals(false, jedis.exists(TEST_PREFIX + "ghost"));
    }
    @Test
    void testDEL() {
        // 正常删除
        jedis.set(TEST_PREFIX + "key1", "value1");
        assertEquals(1, jedis.del(TEST_PREFIX + "key1"));
        assertNull(jedis.get(TEST_PREFIX + "key1"));

        // 删除不存在的key
        assertEquals(0, jedis.del(TEST_PREFIX + "nonexistent"));

        // 批量删除
        jedis.mset(TEST_PREFIX + "key2", "v2", TEST_PREFIX + "key3", "v3");
        assertEquals(2, jedis.del(TEST_PREFIX + "key2", TEST_PREFIX + "key3"));
    }
    @Test//出现过问题，原因设置值为1kb
    void testLargeValue() {
        // 测试大Value（1MB数据）
        String largeValue = new String(new byte[1024 * 1024]);
        assertEquals("OK", jedis.set(TEST_PREFIX + "big", largeValue));
        assertEquals(largeValue.length(), jedis.get(TEST_PREFIX + "big").length());
    }


    @Test
    void testConcurrency() throws InterruptedException {
        int threadCount = 10;
        ExecutorService pool = Executors.newFixedThreadPool(threadCount);
        AtomicInteger success = new AtomicInteger(0);

        // 并发执行INCR
        for (int i = 0; i < threadCount; i++) {
            pool.submit(() -> {
                for (int j = 0; j < 100; j++) {
                    jedis.incr(TEST_PREFIX + "counter");
                    success.incrementAndGet();
                }
            });
        }

        pool.shutdown();
        assertTrue(pool.awaitTermination(10, TimeUnit.SECONDS));
        assertEquals(1000, jedis.get(TEST_PREFIX + "counter"));
        assertEquals(1000, success.get()); // 确保所有操作成功
    }

    @Test//出现过问题，原因计算count出错
    void testHSET_HGET() {
        // 单字段操作
        assertEquals(0, jedis.hset(TEST_PREFIX + "has3", "field2", "asasas我是"));


        assertEquals("asasas我是", jedis.hget(TEST_PREFIX + "has3", "field2"));


    }
    @Test//出现过问题，原因类型转换导致出现异常，返回数据有问题
    void testINCRBY() {//我没给他异常
        jedis.set(TEST_PREFIX + "num", "10");
        assertEquals(15, jedis.incrBy(TEST_PREFIX + "num", 5)); // 10 + 5 = 15

        // 对非数字值操作
        jedis.set(TEST_PREFIX + "text", "abc");
    }
    @Test
    void testSET_GET() {
        // 正常设置和获取
        assertEquals("OK", jedis.set(TEST_PREFIX + "str", "h我"));
        assertEquals("h我", jedis.get(TEST_PREFIX + "str"));

        // 覆盖写入
        jedis.set(TEST_PREFIX + "str", "world");
        assertEquals("world", jedis.get(TEST_PREFIX + "str"));

        // 获取不存在的key
        assertNull(jedis.get(TEST_PREFIX + "unknown"));
    }
    @Test
    void testHGET(){



    }
}