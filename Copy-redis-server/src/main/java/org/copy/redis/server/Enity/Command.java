package org.copy.redis.server.Enity;

import io.netty.channel.ChannelHandlerContext;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Command {//命令提取
    private final ChannelHandlerContext ctx;
    private final String[] rawCommand;

    public Command(ChannelHandlerContext ctx, String[] rawCommand) {
        this.ctx = ctx;
        this.rawCommand = rawCommand;
    }
}

