package org.copy.redis.server.Encoder;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.copy.redis.server.Enity.ReceiveCommand;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
//解析resp
public class RespCommandDecoder extends SimpleChannelInboundHandler<String> {
    private final List<String> args = new ArrayList<>();
    private int expectedArgCount = -1;
    private final ReceiveCommand receiveCommand=new ReceiveCommand();
    private StringBuilder respCommand=null;
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String line) {
        //新命令
        if (line.startsWith("*")) {
            expectedArgCount = Integer.parseInt(line.substring(1));
            respCommand = receiveCommand.getRespCommand();
            respCommand.setLength(0);
            respCommand.append(line).append("\r\n");  //  添加 \r\n
            args.clear();
        } else if (line.startsWith("$")) {

            respCommand.append(line).append("\r\n");  //  添加 \r\n
        } else {
            args.add(line);
            respCommand.append(line).append("\r\n");  //  添加 \r\n
            if (args.size() == expectedArgCount) {
                receiveCommand.setCommand(args.toArray(new String[0]));
                ctx.fireChannelRead(receiveCommand);
                args.clear();
                expectedArgCount = -1;
            }
        }

    }

}

