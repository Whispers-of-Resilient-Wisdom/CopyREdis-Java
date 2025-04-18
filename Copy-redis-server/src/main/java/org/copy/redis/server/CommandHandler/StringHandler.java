package org.copy.redis.server.CommandHandler;

import org.copy.redis.server.DataStructure.RedisObject;
import org.copy.redis.server.DataStructure.RedisServerDS;
import org.copy.redis.server.DataStructure.SDS;
import org.copy.redis.server.Enum.RedisEncoding;
import org.copy.redis.server.Enum.RedisType;
import sun.security.x509.DeltaCRLIndicatorExtension;

/**
 *
 GET,MGET,STRLEN; 读操作

 SET,SETNX,INCR,DECR,INCRBY,DECR,APPEND;写操作
 */
public class StringHandler extends RedisServerDS {//分成需要查看是否存在的，即是否过期
    static  final String One="1";
    static  final String NegativeOne="0";
    //    int INCR( String key){
//
//
//        return  INCRBY(key,One);
//
//
//
//
//    }
//    int DECR(String key){
//            return  INCRBY(key,NegativeOne);
//    }
//    int DECRBY(String key, String value){
//           return INCRBY(key,value);
//
//    }
    boolean Append(){

        return true;
    }//逻辑有点区别，将所有的看作字符串

}
