package org.copy.redis.server.DataStructure;


import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.Arrays;
//大致完成
public class IntSet {
    private byte[] data;
    private int encoding; // 2=int16, 4=int32, 8=int64
    private int length;   // 存储的元素个数

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public int getEncoding() {
        return encoding;
    }

    public void setEncoding(int encoding) {
        this.encoding = encoding;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    // 构造函数，默认 int16
    public IntSet() {
        this.encoding = 2;
        this.length = 0;
        this.data = new byte[4 + 4]; // 存储 encoding 和 length
        updateHeader();
    }

    // 插入整数
    public void add(long value) {
        if (needUpgrade(value)) {
            upgradeEncoding(value);
        }
        insertSorted(value);
    }

    // 检查是否包含整数（二分查找）
    public boolean contains(long value) {
        return binarySearch(value) >= 0;
    }

    // 删除整数
    public boolean remove(long value) {
        int pos = binarySearch(value);
        if (pos < 0) return false; // 未找到
        removeAt(pos);
        return true;
    }

    // 获取所有元素
    public long[] getAll() {
        long[] result = new long[length];
        for (int i = 0; i < length; i++) {
            result[i] = getValueAt(i);
        }
        return result;
    }

    // ========== 私有方法 ==========

    // 判断是否需要升级 encoding
    private boolean needUpgrade(long value) {
        if (encoding == 2 && (value < Short.MIN_VALUE || value > Short.MAX_VALUE)) return true;
        if (encoding == 4 && (value < Integer.MIN_VALUE || value > Integer.MAX_VALUE)) return true;
        return false;
    }

    // 升级 encoding
    private void upgradeEncoding(long newValue) {
        int newEncoding = (newValue > Integer.MAX_VALUE || newValue < Integer.MIN_VALUE) ? 8 : 4;
        byte[] newData = new byte[4 + 4 + length * newEncoding];

        // 复制数据
        ByteBuffer buffer = ByteBuffer.wrap(newData);
        buffer.putInt(newEncoding);
        buffer.putInt(length);
        for (int i = 0; i < length; i++) {
            long value = getValueAt(i);
            putValue(buffer, value, newEncoding);
        }

        this.data = newData;
        this.encoding = newEncoding;
    }

    // 插入整数（保持有序）
    private void insertSorted(long value) {
        ByteBuffer buffer = ByteBuffer.wrap(data);
        buffer.position(4 + 4); // 跳过 encoding 和 length

        int pos = 0;
        while (pos < length && getValueAt(pos) < value) pos++; // 找插入位置

        if (pos < length && getValueAt(pos) == value) return; // 去重

        byte[] newData = new byte[data.length + encoding];
        System.arraycopy(data, 0, newData, 0, buffer.position() + pos * encoding);
        ByteBuffer newBuffer = ByteBuffer.wrap(newData);
        newBuffer.position(buffer.position() + pos * encoding);
        putValue(newBuffer, value, encoding);
        System.arraycopy(data, buffer.position() + pos * encoding, newData, newBuffer.position(), (length - pos) * encoding);

        this.data = newData;
        this.length++;
        updateHeader();
    }

    // 二分查找
    private int binarySearch(long value) {
        int left = 0, right = length - 1;
        while (left <= right) {
            int mid = (left + right) >>> 1;
            long midVal = getValueAt(mid);
            if (midVal < value) left = mid + 1;
            else if (midVal > value) right = mid - 1;
            else return mid;
        }
        return -1;
    }

    // 获取索引位置的值
    private long getValueAt(int index) {
        ByteBuffer buffer = ByteBuffer.wrap(data);
        buffer.position(4 + 4 + index * encoding);
        return encoding == 2 ? buffer.getShort() : encoding == 4 ? buffer.getInt() : buffer.getLong();
    }

    // 删除索引位置的值
    private void removeAt(int index) {
        byte[] newData = new byte[data.length - encoding];
        System.arraycopy(data, 0, newData, 0, 4 + 4 + index * encoding);
        System.arraycopy(data, 4 + 4 + (index + 1) * encoding, newData, 4 + 4 + index * encoding, (length - index - 1) * encoding);
        this.data = newData;
        this.length--;
        updateHeader();
    }

    // 更新头部信息
    private void updateHeader() {
        ByteBuffer buffer = ByteBuffer.wrap(data);
        buffer.putInt(0, encoding);
        buffer.putInt(4, length);
    }

    // 向 ByteBuffer 存入整数
    private void putValue(ByteBuffer buffer, long value, int encoding) {
        if (encoding == 2) buffer.putShort((short) value);
        else if (encoding == 4) buffer.putInt((int) value);
        else buffer.putLong(value);
    }

    // ========== 测试 ==========
    public static void main(String[] args) throws UnsupportedEncodingException {
        IntSet intSet = new IntSet();
        intSet.add(10);
        intSet.add(100);
        intSet.add(30000); // 触发 int16 -> int32 升级
        intSet.add(10000000000L); // 触发 int32 -> int64 升级
        intSet.add(100);
        System.setOut(new java.io.PrintStream(System.out, true, "UTF-8"));
        int length = intSet.getData().length;
        System.out.println(length);
        System.out.println("所有元素：" + Arrays.toString(intSet.getAll())); // [10, 100, 30000, 10000000000]
        System.out.println("包含 1000？ " + intSet.contains(1000)); // false
        System.out.println("包含 30000？ " + intSet.contains(30000)); // true

        intSet.remove(100);

        System.out.println("删除 100 后：" + Arrays.toString(intSet.getAll())); // [10, 30000, 10000000000]
    }
}
