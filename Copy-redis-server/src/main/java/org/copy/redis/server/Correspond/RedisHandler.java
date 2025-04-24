package org.copy.redis.server.Correspond;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.copy.redis.server.Enity.Command;
import org.copy.redis.server.Enity.ReceiveCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//这里主要处理关于参数出错
//处理命令步骤--收到命令，多线程判断参数合法-->单线程惰性删除->处理命令
public class RedisHandler extends SimpleChannelInboundHandler<ReceiveCommand> {
    private static final Logger logger = LoggerFactory.getLogger(RedisHandler.class);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ReceiveCommand msg) {
        String[] command = msg.getCommand();

      CommandProcessor.submit(new Command(ctx, command, msg.getRespCommand().toString()));
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
