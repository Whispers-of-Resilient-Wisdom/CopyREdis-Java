package org.copy.redis.server.interfaces;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
//RDB功能
public interface RDBService {
    void saveToFile(File file);                // 保存内存快照
    void loadFromFile(File file);              // 从文件恢复
    void saveToStream(OutputStream out);       // 主从时发送快照
    void loadFromStream(InputStream in);       // 从机接收快照
}
