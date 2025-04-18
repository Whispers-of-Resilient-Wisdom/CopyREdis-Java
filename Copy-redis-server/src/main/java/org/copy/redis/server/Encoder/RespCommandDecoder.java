package org.copy.redis.server.Encoder;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.ArrayList;
import java.util.List;
//解析resp
public class RespCommandDecoder extends SimpleChannelInboundHandler<String> {
    private List<String> args = new ArrayList<>();
    private int expectedArgCount = -1;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String line) throws Exception {
        // 如果以 * 开头，说明是一个数组（即一个命令请求）
        if (line.startsWith("*")) {
            expectedArgCount = Integer.parseInt(line.substring(1)); // 获取参数个数
            args.clear(); // 清空上一个命令留下的参数缓存
        }
        // 如果以 $ 开头，表示接下来是一个 Bulk String 的长度描述，这里可以跳过它
        else if (line.startsWith("$")) {
            // RESP 协议中的 $x 表示下一个行是字符串，长度为 x
            // 我们的处理器直接跳过这行长度描述
        }
        // 正常的字符串参数，直接收集
        else {
            args.add(line); // 添加参数字符串

            // 如果参数个数已达到预期值，说明一个命令收集完毕
            if (args.size() == expectedArgCount) {
                ctx.fireChannelRead(args.toArray(new String[0])); // 向后续的 Handler 传递 String[] 参数数组
                args.clear(); // 重置缓存，准备下一条命令
                expectedArgCount = -1;
            }
        }
    }

}

