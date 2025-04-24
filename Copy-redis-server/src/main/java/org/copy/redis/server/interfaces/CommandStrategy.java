package org.copy.redis.server.interfaces;
//命令的处理
public interface CommandStrategy {
    String execute(String[] args);
}

