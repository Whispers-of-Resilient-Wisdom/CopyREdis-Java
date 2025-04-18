package org.copy.redis.server.Correspond;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

import java.net.SocketException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class MiniRedisServer {
    private static final Map<String, String> database = new HashMap();

    public MiniRedisServer() {
    }

    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup boss = new NioEventLoopGroup();
        EventLoopGroup worker = new NioEventLoopGroup();

        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            ((ServerBootstrap)bootstrap.group(boss, worker).channel(NioServerSocketChannel.class)).childHandler(new ChannelInitializer<SocketChannel>() {
                protected void initChannel(SocketChannel ch) {
                    ch.pipeline().addLast(new ChannelHandler[]{new LineBasedFrameDecoder(1024)});
                    ch.pipeline().addLast(new ChannelHandler[]{new StringDecoder()});
                    ch.pipeline().addLast(new ChannelHandler[]{new StringEncoder()});
                    ch.pipeline().addLast(new ChannelHandler[]{new StringDecoder(CharsetUtil.UTF_8)});
                    ch.pipeline().addLast(new ChannelHandler[]{new RespCommandDecoder()});
                    ch.pipeline().addLast(new ChannelHandler[]{new RedisHandler()});
                }
            });
            ChannelFuture future = bootstrap.bind(6379).sync();
            System.out.println("Mini Redis Server started on port 6379");
            future.channel().closeFuture().sync();
        } finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }

    }

    static class RedisHandler extends SimpleChannelInboundHandler<String[]> {
        RedisHandler() {
        }

        protected void channelRead0(ChannelHandlerContext ctx, String[] args) {
            if (args.length == 0) {
                ctx.writeAndFlush("-ERR Empty command\r\n");
            } else {
                switch (args[0].toUpperCase(Locale.ROOT)) {
                    case "PING":
                        ctx.writeAndFlush("+PONG\r\n");
                        break;
                    case "ECHO":
                        if (args.length < 2) {
                            ctx.writeAndFlush("-ERR wrong number of arguments for 'echo'\r\n");
                        } else {
                            ctx.writeAndFlush("$" + args[1].length() + "\r\n" + args[1] + "\r\n");
                        }
                        break;
                    case "SET":
                        if (args.length < 3) {
                            ctx.writeAndFlush("-ERR wrong number of arguments for 'set'\r\n");
                        } else {
                            MiniRedisServer.database.put(args[1], args[2]);
                            ctx.writeAndFlush("+OK\r\n");
                        }
                        break;
                    case "GET":
                        String val = (String)MiniRedisServer.database.get(args[1]);
                        if (val == null) {
                            ctx.writeAndFlush("$-1\r\n");
                        } else {
                            ctx.writeAndFlush("$" + val.length() + "\r\n" + val + "\r\n");
                        }
                        break;
                    default:
                        ctx.writeAndFlush("-ERR unknown command '" + args[0] + "'\r\n");
                }

            }
        }

        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
            if (cause instanceof SocketException && cause.getMessage().contains("Connection reset")) {
                System.out.println("客户端主动断开连接，忽略 Connection reset");
            } else {
                cause.printStackTrace();
            }

            ctx.close();
        }
    }
}
