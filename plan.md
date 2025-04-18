```mermaid
graph TD;
  A[启动服务端] -->|监听| B[监听端口]
  B -->|等待连接| C[等待客户端连接]
  C -->|接收请求| D[接收客户端请求]
  D -->|解析协议| E[解析RESP协议]
  E -->|执行| F[执行命令]
  F -->|操作数据| G[操作内存数据结构]
  G -->|生成| H[生成响应]
  H -->|返回| I[返回结果给客户端]
  I -->|等待下一个请求| C

  J[启动客户端] -->|连接| K[连接服务端]
  K -->|发送命令| L[发送RESP格式命令]
  L -->|等待| M[等待响应]
  M -->|解析| N[解析响应]
  N -->|显示| O[显示结果]
  O -->|等待下一个请求| L

  style A fill:#FFD700,stroke:#000,stroke-width:2px;
  style B fill:#FFA500,stroke:#000;
  style C fill:#FF8C00,stroke:#000;
  style D fill:#1E90FF,stroke:#000;
  style E fill:#4169E1,stroke:#000;
  style F fill:#4682B4,stroke:#000;
  style G fill:#5F9EA0,stroke:#000;
  style H fill:#3CB371,stroke:#000;
  style I fill:#2E8B57,stroke:#000;

  style J fill:#DC143C,stroke:#000,stroke-width:2px;
  style K fill:#B22222,stroke:#000;
  style L fill:#FF4500,stroke:#000;
  style M fill:#FF6347,stroke:#000;
  style N fill:#CD5C5C,stroke:#000;
  style O fill:#8B0000,stroke:#000;
