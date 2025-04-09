package org.copy.redis.server.DataStructure;



public class ZipList {
 private byte[] data;
 private int zlbytes;  // 当前压缩列表的字节数
 private int zltail;   // 压缩列表尾部的索引
 private int zllen;    // 压缩列表的长度

 // 构造函数，初始化一个空的压缩列表
 public ZipList() {
  this.data = new byte[1024];  // 假设初始大小为 1024 字节
  this.zlbytes = 0;
  this.zltail = 0;
  this.zllen = 0;
 }


}

