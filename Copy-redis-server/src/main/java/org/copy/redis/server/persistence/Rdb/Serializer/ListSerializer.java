package org.copy.redis.server.persistence.Rdb.Serializer;

import org.copy.redis.server.DataStructure.RedisLinkedList;
import org.copy.redis.server.DataStructure.RedisObject;
import org.copy.redis.server.interfaces.RDBSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;

public class ListSerializer implements RDBSerializer {
    private static final Logger logger = LoggerFactory.getLogger(ListSerializer.class);
    @Override
    public void Serializer(RedisObject redisObj, DataOutputStream dos) {
        List<String> list = ((RedisLinkedList) (redisObj.getPtr())).getList();

        try {
            dos.writeInt(list.size()); // 元素个数
            for (String item : list) {//value
                byte[] itemBytes = item.getBytes();
                dos.writeInt(itemBytes.length);
                dos.write(itemBytes);
            }
        } catch (IOException e) {
            logger.error("ListSerializer出错{}",e.getMessage());
        }
    }
}