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

public class RedisServer {


    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup boss = new NioEventLoopGroup();
        EventLoopGroup worker = new NioEventLoopGroup();

        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(boss, worker)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) {//max最大为2MB
                            ch.pipeline().addLast(new LineBasedFrameDecoder(1024*1024*2));
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


}

