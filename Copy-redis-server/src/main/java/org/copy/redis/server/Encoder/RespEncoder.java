package org.copy.redis.server.Encoder;
public class RespEncoder {

    /**
     * 简单字符串类型
     * Redis 中如：+OK\r\n、+PONG\r\n
     */
    public static String simpleString(String msg) {
        return "+" + msg + "\r\n";
    }

    /**
     * 错误类型
     * Redis 中如：-ERR something went wrong\r\n
     */
    public static String error(String msg) {
        return "-" + msg + "\r\n";
    }

    /**
     * 整数类型
     * Redis 中如：:1000\r\n
     */
    public static String integer(long val) {
        return ":" + val + "\r\n";
    }

    /**
     * 批量字符串类型
     * Redis 中如：$6\r\nfoobar\r\n
     * 如果是 null，表示空字符串（$-1\r\n）
     */
    public static String bulkString(String val) {
        if (val == null) return "$-1\r\n"; // 表示 Redis 的空值 null
        return "$" + val.length() + "\r\n" + val + "\r\n";
    }

    /**
     * 数组类型（支持嵌套）
     */
    public static String array(String[] values) {
        if (values == null) return "*-1\r\n"; // 空数组
        StringBuilder sb = new StringBuilder();
        sb.append("*").append(values.length).append("\r\n");
        for (String v : values) {
            sb.append(bulkString(v)); // 每个元素都是一个 bulkString 类型
        }
        return sb.toString();
    }
}
