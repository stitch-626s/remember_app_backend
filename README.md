# Remember App（后端部分）

> Remember App 是一个面向大规模题库学习的练题管理系统后端服务，提供完善的 API 接口与业务逻辑支持。

Remember App 后端采用 Spring Boot 3.4.3 构建，支持高并发的题库管理与学习场景。

## 技术栈

- Spring Boot 3.4.3
- MySQL 8.0
- Java 18

## 快速开始

### 准备环境

- JDK 18 或更高版本
- MySQL 8.0
- Maven 3.6+

### 配置数据库

在 `src/main/resources/application.yml` 中配置数据库连接：

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/remember?useUnicode=true&characterEncoding=utf-8
    username: your_username
    password: your_password
```

### 启动服务

```bash
mvn spring-boot:run
```

服务默认启动于 http://localhost:8080

### 构建打包

```bash
mvn clean package -DskipTests
```

构建产物输出至 `target/remember-0.0.1-SNAPSHOT.jar`

## 相关链接

- 前端仓库：https://github.com/stitch-626s/remember_app_fronted
- 后端仓库：https://github.com/stitch-626s/remember_app_backend
