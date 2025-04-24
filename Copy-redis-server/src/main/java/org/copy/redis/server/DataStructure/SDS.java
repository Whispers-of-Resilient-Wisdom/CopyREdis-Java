package org.copy.redis.server.DataStructure;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SDS {
    private StringBuilder builder;
    //int capacity 存储字符串的字符序列 //对应builder.capacity()
    //int count 表示实际使用的字符数 对应 Buider.length()
    //char[] value  value 数组的长度。
    private int free;

    public int getFree() {
        this.free=builder.capacity()-builder.length();
        return free;
    }



    static SDS SDSNew(){
         SDS f=new SDS();
         StringBuilder builder = new StringBuilder();
         f.setBuilder(builder);
         return f;
     }
     public SDS(){


     }
    public SDS(String initialValue) {
        this.builder = new StringBuilder(initialValue);
    }

    public void append(String str) {
        builder.append(str);
    }
    static  public  boolean  clear(SDS sds){
        sds.getBuilder().setLength(0);

        return true;
    }
    public String toString() {
        return builder.toString();
    }

    public int length() {
        return builder.length();
    }
}
