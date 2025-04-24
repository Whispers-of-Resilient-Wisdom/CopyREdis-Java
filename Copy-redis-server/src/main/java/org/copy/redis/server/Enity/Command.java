package org.copy.redis.server.Enity;

import io.netty.channel.ChannelHandlerContext;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Command {//命令提取
    private final ChannelHandlerContext ctx;
    private final String[] rawCommand;
    private  final String RespCommand ;


    public Command(ChannelHandlerContext ctx ,String[] rawCommand, String respCommand) {
        this.ctx = ctx;
        this.rawCommand = rawCommand;
        RespCommand = respCommand;
    }
    //可以放在这里加个方法判断 type,命令长度 --
}

