package org.copy.redis.server.Correspond;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.copy.redis.server.Enity.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RedisHandler extends SimpleChannelInboundHandler<String[]> {
    private static final Logger logger = LoggerFactory.getLogger(RedisHandler.class);
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String []msg) {

      CommandProcessor.submit(new Command(ctx, msg));
    }
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        if (cause instanceof java.net.SocketException && cause.getMessage().contains("Connection reset")) {
            System.out.println("客户端主动断开连接，忽略 Connection reset");
        } else {
            logger.error(" 其他异常{}",cause.getMessage());// 其他异常还是要打
        }
        ctx.close(); // 关闭连接
    }

}
