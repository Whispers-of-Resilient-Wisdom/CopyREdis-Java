package org.copy.redis.server.DataStructure;

import java.util.HashMap;
import java.util.Map;

public class RedisDict {
    private Map<String, String> dict;

    public RedisDict() {
        this.dict = new HashMap<>();
    }

    // 插入数据
    public void set(String key, String value) {
        dict.put(key, value);
    }

    // 查找数据
    public String get(String key) {
        return dict.get(key);
    }

    // 删除数据
    public void delete(String key) {
        dict.remove(key);
    }

    // 检查键是否存在
    public boolean containsKey(String key) {
        return dict.containsKey(key);
    }
}

