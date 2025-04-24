package org.copy.redis.server.persistence.Rdb.Serializer;

import org.copy.redis.server.DataStructure.RedisHashTable;
import org.copy.redis.server.DataStructure.RedisObject;
import org.copy.redis.server.interfaces.RDBSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Set;

public class SetSerializer implements RDBSerializer {
    private static final Logger logger = LoggerFactory.getLogger(SetSerializer.class);
    @Override
    public void Serializer(RedisObject redisObj, DataOutputStream dos) {
        // value
        Set<String> set = ((RedisHashTable) (redisObj.getPtr())).getSet();
        try {
            dos.writeInt(set.size()); // 元素个数
            for (String item : set) {//value
                byte[] itemBytes = item.getBytes();
                dos.writeInt(itemBytes.length);
                dos.write(itemBytes);
            }
        } catch (IOException e) {
            logger.error("SetSerializer出错{}",e.getMessage());
        }
    }
}
