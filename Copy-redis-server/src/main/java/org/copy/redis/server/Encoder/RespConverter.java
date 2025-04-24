package org.copy.redis.server.Encoder;
import java.nio.charset.StandardCharsets;

public class RespConverter {

    /**
     * 将命令数组转换为 RESP 协议格式的字符串
     * @param commandParts 命令部分数组，如 ["SET", "key", "value"]
     * @return RESP 格式字符串，如 "*3\r\n$3\r\nSET\r\n$3\r\nkey\r\n$5\r\nvalue\r\n"
     */

    public static String toRespFormat(String[] commandParts) {
        StringBuilder respBuilder = new StringBuilder();

        // 添加数组长度标识
        respBuilder.append("*").append(commandParts.length).append("\r\n");

        // 添加每个参数
        for (String part : commandParts) {
            byte[] bytes = part.getBytes(StandardCharsets.UTF_8);
            respBuilder.append("$").append(bytes.length).append("\r\n");
            respBuilder.append(part).append("\r\n");
        }

        return respBuilder.toString();
    }

    /**
     * 将命令数组转换为 RESP 协议格式的字节数组
     * @param commandParts 命令部分数组
     * @return RESP 格式的字节数组
     */
    public static byte[] toRespBytes(String[] commandParts) {
        return toRespFormat(commandParts).getBytes(StandardCharsets.UTF_8);
    }

    /**
     * 检查是否是有效的 RESP 命令
     */
    public static boolean isValidRespCommand(String[] commandParts) {
        if (commandParts == null || commandParts.length == 0) {
            return false;
        }
        for (String part : commandParts) {
            if (part == null) {
                return false;
            }
        }
        return true;
    }
}
