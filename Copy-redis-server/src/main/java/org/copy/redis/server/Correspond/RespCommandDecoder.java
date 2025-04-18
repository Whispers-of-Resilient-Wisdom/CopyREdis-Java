package org.copy.redis.server.Correspond;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.ArrayList;
import java.util.List;

public class RespCommandDecoder extends SimpleChannelInboundHandler<String> {
    private List<String> args = new ArrayList();
    private int expectedArgCount = -1;

    public RespCommandDecoder() {
    }

    protected void channelRead0(ChannelHandlerContext ctx, String line) throws Exception {
        if (line.startsWith("*")) {
            this.expectedArgCount = Integer.parseInt(line.substring(1));
            this.args.clear();
        } else if (!line.startsWith("$")) {
            this.args.add(line);
            if (this.args.size() == this.expectedArgCount) {
                ctx.fireChannelRead(this.args.toArray(new String[0]));
                this.args.clear();
                this.expectedArgCount = -1;
            }
        }

    }
}
