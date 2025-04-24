package org.copy.redis.server.Enity;

import lombok.Getter;

import java.util.LinkedList;
@Getter//单实例 --一台机子只需要一个缓冲区
public class ReplicationBacklog {
    private final byte[] buffer;
    private final int maxSize;
    private long globalOffset = 0;
    private int writePos = 0;
    private static class Holder {//单实例 ，适合java
        private static final ReplicationBacklog INSTANCE = new ReplicationBacklog();
    }

    // 对外获取实例方法
    public static ReplicationBacklog getInstance() {
        return Holder.INSTANCE;
    }


    private static class EntryMeta {
        long offset;
        int pos;
        int len;

        EntryMeta(long offset, int pos, int len) {
            this.offset = offset;
            this.pos = pos;
            this.len = len;
        }
    }
     ReplicationBacklog(){
        this.maxSize = 1024*1024;
        this.buffer = new byte[maxSize];


    }
    private final LinkedList<EntryMeta> index = new LinkedList<>();

    private ReplicationBacklog(int maxSize) {
        this.maxSize = maxSize;
        this.buffer = new byte[maxSize];
    }

    public  void append(byte[] data) {
        if (data.length > maxSize) {
            globalOffset += data.length;//他就算超过了长度，也要加
            return;} // 防止单条数据太大
        int len = data.length;
        int endPos = (writePos + len) % maxSize;

        // 移除会被覆盖的旧数据
        index.removeIf(meta -> isOverwritten(meta.pos, meta.len, writePos, endPos));

        // 分段写入
        int firstPart = Math.min(len, maxSize - writePos);
        System.arraycopy(data, 0, buffer, writePos, firstPart);
        if (firstPart < len) {
            System.arraycopy(data, firstPart, buffer, 0, len - firstPart);
        }

        index.add(new EntryMeta(globalOffset, writePos, len));
        writePos = endPos;
        globalOffset += len;
    }

    public  byte[] readFromOffset(long offset) {
        for (EntryMeta meta : index) {
            if (meta.offset == offset) {
                byte[] result = new byte[meta.len];
                if (meta.pos + meta.len <= maxSize) {//不需要额外操作
                    System.arraycopy(buffer, meta.pos, result, 0, meta.len);
                } else {
                    int firstPart = maxSize - meta.pos;
                    System.arraycopy(buffer, meta.pos, result, 0, firstPart);
                    System.arraycopy(buffer, 0, result, firstPart, meta.len - firstPart);
                }
                return result;
            }
        }
        return null; // offset 不在 backlog 中
    }



    private boolean isOverwritten(int pos, int len, int newStart, int newEnd) {
        if (newStart < newEnd) {
            return (pos >= newStart && pos < newEnd) || (pos + len - 1 >= newStart && pos + len - 1 < newEnd);
        } else {
            return (pos >= newStart || pos < newEnd) || ((pos + len - 1) >= newStart || (pos + len - 1) < newEnd);
        }
    }
//测试成功 覆盖正确
    public static void main(String[] args) {
        ReplicationBacklog backlog = new ReplicationBacklog(6);
        //offset
        backlog.append("abc".getBytes()); // offset 0
        backlog.append("cd".getBytes()); // offset 3
        backlog.append("ef".getBytes()); // offset 5-1
        backlog.append("gh".getBytes());
        byte[] bytes = backlog.readFromOffset(0);
        if(bytes != null) {
        System.out.println(new String(backlog.readFromOffset(0)));}
        ; // 预期: abc
        System.out.println(new String(backlog.readFromOffset(3))); // 预期: cd
        System.out.println(new String(backlog.readFromOffset(5)));
        System.out.println(new String(backlog.readFromOffset(7)));
        // 预期: ef




    }

}
