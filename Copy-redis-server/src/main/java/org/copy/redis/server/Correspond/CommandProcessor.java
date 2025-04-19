package org.copy.redis.server.Correspond;

import io.netty.channel.ChannelHandlerContext;
import org.copy.redis.server.Enity.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import static org.copy.redis.server.CommandHandler.CommandContext.executeCommand;
public class CommandProcessor {
    private static final BlockingQueue<Command> commandQueue = new LinkedBlockingQueue<>();
    private static final Logger logger = LoggerFactory.getLogger(CommandProcessor.class);
    private static volatile Thread processorThread;

    static {
        startProcessorThread();
    }

    private static void startProcessorThread() {
        processorThread = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    Command cmd = commandQueue.take();
                    logger.debug("Processing command by {}: {}",
                            Thread.currentThread().getName(), cmd.getRawCommand());
                    handle(cmd);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    logger.info("Processor thread interrupted");
                    break;
                } catch (Exception e) {
                    logger.error("Command handling failed", e);
                }
            }
        }, "Redis-Command-Processor");
        processorThread.setDaemon(true); // 设为守护线程（可选）
        processorThread.start();
    }

    public static void submit(Command command) {
        if (!commandQueue.offer(command)) {
            logger.warn("Command queue full, dropping: {}", command.getRawCommand());
            sendErrorResponse(command, "-ERR server busy\r\n");
        }
    }

    private static void handle(Command command) {
        String[] parts = command.getRawCommand();
        if (parts == null || parts.length == 0) {
            sendErrorResponse(command, "-ERR empty command\r\n");
            return;
        }
        String response = executeCommand(parts);
        command.getCtx().writeAndFlush(response);
    }

    private static void sendErrorResponse(Command cmd, String error) {
        if (cmd != null && cmd.getCtx() != null) {
            cmd.getCtx().writeAndFlush(error);
        }
    }

    public static void shutdown() {
        if (processorThread != null) {
            processorThread.interrupt();
        }
    }
}