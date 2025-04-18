package org.copyredis.testredisserver;

import jakarta.annotation.Resource;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.connection.RedisServer;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootApplication
public class TestRedisServerApplication implements CommandLineRunner {
@Resource
private RedisTemplate<String, String> redisTemplate;
    public static void main(String[] args) {
        SpringApplication.run(TestRedisServerApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        redisTemplate.opsForValue().set("key1", "value1");
        String s = redisTemplate.opsForValue().get("key2");
        System.out.println(s);
    }
}
