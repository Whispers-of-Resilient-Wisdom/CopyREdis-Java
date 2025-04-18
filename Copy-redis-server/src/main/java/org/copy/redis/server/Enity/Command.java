package org.copy.redis.server.Enity;


import io.netty.channel.ChannelHandlerContext;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Command {
    public final ChannelHandlerContext ctx;
    private   String[] rawCommand;

    public Command(ChannelHandlerContext ctx, String[] rawCommand) {
        this.ctx = ctx;
        this.rawCommand = rawCommand;
    }
}
