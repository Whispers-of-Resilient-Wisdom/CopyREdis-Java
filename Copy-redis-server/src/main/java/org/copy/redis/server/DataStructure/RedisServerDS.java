package org.copy.redis.server.DataStructure;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
@Getter
@Setter
public class RedisServerDS {

    protected static HashMap<String,RedisObject> map=new HashMap<>();//key is SDS,value is 数据
    protected static HashMap<String,Long> expire=new HashMap<>(); //过期的key- 时间戳

}