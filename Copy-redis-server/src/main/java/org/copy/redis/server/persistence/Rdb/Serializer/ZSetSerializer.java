package org.copy.redis.server.persistence.Rdb.Serializer;

import org.copy.redis.server.DataStructure.RedisObject;
import org.copy.redis.server.DataStructure.RedisZset;
import org.copy.redis.server.interfaces.RDBSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class ZSetSerializer implements RDBSerializer {
    private static final Logger logger = LoggerFactory.getLogger(ZSetSerializer.class);
    @Override
    public void Serializer(RedisObject redisObj, DataOutputStream dos) {
        Map<String, Double> dict = ((RedisZset) (redisObj.getPtr())).getDict();

        try {
            dos.writeInt(dict.size()); // 元素个数
            for (Map.Entry<String, Double> entry : dict.entrySet()) {
                byte[] memberBytes = entry.getKey().getBytes(StandardCharsets.UTF_8);//成员
                dos.writeInt(memberBytes.length);
                dos.write(memberBytes);
                dos.writeDouble(entry.getValue()); // score
            }
        } catch (IOException e) {
            logger.error("ZSetSerializer出错{}",e.getMessage());
        }
    }
}
