package org.copy.redis.server.persistence.Rdb.Serializer;

import lombok.Getter;
import org.copy.redis.server.DataStructure.RedisObject;
import org.copy.redis.server.Enum.RedisType;
import org.copy.redis.server.interfaces.RDBSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
@Getter //序列化只需要一个
public class RDBSerializerContext {
    private static final Logger logger = LoggerFactory.getLogger(RDBSerializerContext.class);
    private final  static Map<Byte, RDBSerializer> registry=new HashMap<>() ;

    static {
        registry.put(RedisType.REDIS_STRING.getType(), new StringSerializer());
        registry.put(RedisType.REDIS_LIST.getType(), new ListSerializer());
        registry.put(RedisType.REDIS_SET.getType(), new SetSerializer());
        registry.put(RedisType.REDIS_ZSET.getType(), new ZSetSerializer());
        registry.put(RedisType.REDIS_HASH.getType(), new HashSerializer());
    }

  static public void SerializerHandler(String key, RedisObject obj, DataOutputStream dos){
        byte type=obj.getType();
        RDBSerializer rdbSerializer = registry.get(type);
        if(rdbSerializer!=null){
            try {
                // type
                dos.writeByte(type);
                // key
                byte[] keyBytes = key.getBytes(StandardCharsets.UTF_8);
                dos.writeInt(keyBytes.length);
                dos.write(keyBytes);//key部分
            } catch (IOException e) {
                logger.error("序列化key失败:{}",e.getMessage());
            }
            rdbSerializer.Serializer(obj, dos);

        }else{
            logger.info("当前没有此方式可序列化");

        }

  }



}
