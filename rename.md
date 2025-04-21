Copy Redis 是一个用 java 语言实现的 Redis 服务器。本项目旨在为尝试使用 java语言开发NoSQL数据库 。
## 📦 快速开始

### 依赖要求

- JDK 11+
- Maven 3.6+

### 安装步骤

```bash
git clone https://github.com/Whispers-of-Resilient-Wisdom/CopyREdis-Java.git
cd CopyREdis-Java
git pull origin master(假设设置为origin)

```

关键功能:
- 支持 string, list, hash, set, sorted set 数据结构
- 自动过期功能(TTL)
- 并行引擎, 无需担心您的操作会阻塞整个服务器.

可以在[我的博客](https://www.cnblogs.com/ppx-is-me )了解更多关于
java redis 的信息。

# 运行 Java redis

java redis 默认监听 0.0.0.0:6399，可以使用 java redis使用数据库

## 支持的命令

请参考[命令] (https://github.com/Whispers-of-Resilient-Wisdom/CopyREdis-Java/blob/master/command.md)




## 开发计划

+ [x]  数据结构搭建完成
+ [x]  处理命令完成
+ [ ] 加载 AOF 文件
+ [ ] 主从模式


## 如何阅读源码

本项目的目录结构:

- 根目录: org.copy.redis.server
- interfaces: 一些模块间的接口

建议按照下列顺序阅读各包:

- cnsspond: tcp  netty 实现
- Encoder: redis 协议解析器,生成器
- datastruct: redis 的各类数据结构实现
    - dict: hash 表
    - redislinkedst: 链表
    - set： 基于hash表的集合
    - sortedset: skiplist+hashmap 
- database: 存储引擎核心
    - Redisserver.java: redis 服务实例, 支持多数据库, 持久化, 主从复制等能力
- aof: AOF 持久化实现 
