# SummerChat Login Service
## 基于Spring boot构建的微服务   

---

### 项目介绍  

---

### 功能
 - 用户登录验证
 - 用户的注册功能
 - 找回密码
---
### 使用的技术栈
 - Spring boot
 - MySQL
 - Mybatis
 - Redis
 - Nacos
 - SaToken
 - Spring Mail
 - FastJson
---
### 项目模块与结构  
 - 启动类包含SummerChatLoginApplication与application.properties配置文件：[core](./core)  
 - 通用常用组件，包含邮件发送服务，User实体：[common-module](./common-module)  
 - 登录服务，包含用户使用账号登录服务返回token给前端，与提供用户找回密码：[login-service](./login-service)  
 - 注册服务，包含用户注册功能：[register-service](./registration-service)  
---
### 项目运行需要
 - 安装MySQL数据库，并创建数据库，数据库名称为：`user`
同时创建表
```mysql
CREATE TABLE `user` (
  `uin` bigint NOT NULL AUTO_INCREMENT COMMENT '账号',
  `encrypted_password` varchar(64) NOT NULL COMMENT '密码',
  `email` varchar(32) NOT NULL,
  PRIMARY KEY (`uin`),
  UNIQUE KEY `uk_mail` (`email`),
  UNIQUE KEY `idx_login` (`uin`,`encrypted_password`,`email`) COMMENT '登录索引 校验账号和密码等'
) ENGINE=InnoDB AUTO_INCREMENT=10000010 DEFAULT CHARSET=utf8mb3 COMMENT='用户表'


```
 - 安装Redis
 - 安装Nacos服务
