package org.copy.redis.server.Correspond;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.copy.redis.server.Enity.Command;

public class RedisHandler extends SimpleChannelInboundHandler<String[]> {
    public RedisHandler() {
    }

    protected void channelRead0(ChannelHandlerContext ctx, String[] msg) {
        CommandProcessor.submit(new Command(ctx, msg));
    }
}
