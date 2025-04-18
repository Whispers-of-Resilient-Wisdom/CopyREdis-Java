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
import org.copy.redis.server.Encoder.RespCommandDecoder;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
//test
public class MiniRedisServer {

    private static final Map<String, String> database = new HashMap<>();

    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup boss = new NioEventLoopGroup();
        EventLoopGroup worker = new NioEventLoopGroup();

        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(boss, worker)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) {
                            ch.pipeline().addLast(new LineBasedFrameDecoder(1024));
                            ch.pipeline().addLast(new StringDecoder());
                            ch.pipeline().addLast(new StringEncoder());
                            // 每行一个 RESP 块
                            ch.pipeline().addLast(new StringDecoder(CharsetUtil.UTF_8)); // 字符串
                            ch.pipeline().addLast(new RespCommandDecoder()); // 把 RESP 解析成 String[]
                            // 最终业务逻辑
                            ch.pipeline().addLast(new RedisHandler());
                        }
                    });

            ChannelFuture future = bootstrap.bind(6379).sync();
            System.out.println("Redis Server started on port 6379");
            future.channel().closeFuture().sync();
        } finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }

    static class RedisHandler extends SimpleChannelInboundHandler<String[]> {
        protected void channelRead0(ChannelHandlerContext ctx, String[] args) {
            if (args.length == 0) {
                ctx.writeAndFlush("-ERR Empty command\r\n");
                return;
            }

            String cmd = args[0].toUpperCase(Locale.ROOT);
            switch (cmd) {
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
                        database.put(args[1], args[2]);
                        ctx.writeAndFlush("+OK\r\n");
                    }
                    break;
                case "GET":
                    String val = database.get(args[1]);
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
        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
            if (cause instanceof java.net.SocketException && cause.getMessage().contains("Connection reset")) {
                System.out.println("客户端主动断开连接，忽略 Connection reset");
            } else {
                cause.printStackTrace(); // 其他异常还是要打
            }
            ctx.close(); // 关闭连接
        }


    }
}

