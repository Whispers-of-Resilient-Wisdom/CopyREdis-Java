package org.copy.redis.server.Correspond;

import io.netty.channel.ChannelHandlerContext;
import org.copy.redis.server.Enity.Command;

import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.*;

public class CommandProcessor {
    private static final BlockingQueue<Command> commandQueue = new LinkedBlockingQueue<>();
    private static final Map<String, String> database = new ConcurrentHashMap<>();

    static {
        // 启动单线程处理命令
        new Thread(() -> {
            while (true) {
                try {
                    Command cmd = commandQueue.take();  // 阻塞式取出
                    handle(cmd);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, "Redis-Command-Processor").start();
    }

    public static void submit(Command command) {
        commandQueue.offer(command);
    }

    private static void handle(Command command) {
        String[] parts=command.getRawCommand();
        ChannelHandlerContext ctx = command.getCtx();

        if (parts.length == 0) {
            ctx.writeAndFlush("-ERR empty command\r\n");
            return;
        }

        String cmd = parts[0].toUpperCase();
        switch (cmd) {
            case "SET":
                if (parts.length != 3) {
                    ctx.writeAndFlush("-ERR wrong number of arguments for 'set'\r\n");
                } else {
                    database.put(parts[1], parts[2]);
                    ctx.writeAndFlush("+OK\r\n");
                }
                break;
            case "GET":
                if (parts.length != 2) {
                    ctx.writeAndFlush("-ERR wrong number of arguments for 'get'\r\n");
                } else {
                    String value = database.get(parts[1]);
                    if (value != null) {
                        ctx.writeAndFlush("$" + value.length() + "\r\n" + value + "\r\n");
                    } else {
                        ctx.writeAndFlush("$-1\r\n");
                    }
                }
                break;
            default:
                ctx.writeAndFlush("-ERR unknown command\r\n");
        }
    }
}

