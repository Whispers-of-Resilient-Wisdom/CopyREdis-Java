package org.copy.redis.server.interfaces;

public interface CommandStrategy {
    String execute(String[] args);
}

