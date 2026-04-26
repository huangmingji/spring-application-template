# Spring Boot 项目模板

一个功能完善的 Spring Boot 3.5.8 项目模板，集成了用户认证、权限管理、文件存储和消息发送等功能。

## 技术栈

| 技术 | 版本 | 说明 |
|------|------|------|
| Spring Boot | 3.5.8 | 核心框架 |
| Java | 21 | 编程语言 |
| MyBatis | 3.0.3 | ORM 框架 |
| MySQL | 8.0 | 数据库 |
| Redis | - | 缓存 |
| Spring Security | 6.x | 安全框架 |
| JWT | 0.12.6 | 身份认证 |

## 项目结构

```
src/main/java/com/stargazer/springapplicationtemplate/
├── config/           # 配置类
│   ├── SecurityConfig.java        # 安全配置
│   ├── StorageConfig.java         # 存储配置
│   ├── DataInitializer.java       # 数据初始化
│   └── I18nConfig.java        # 多语言配置
├── controllers/      # 控制器
├── domain/           # 实体类
├── repositories/     # 数据访问层（MyBatis）
├── services/         # 服务层
│   ├── interfaces/              # 服务接口
│   └── models/                  # DTO/Model
├── filter/           # 过滤器
│   ├── JwtAuthenticationFilter.java
│   └── LocaleChangeInterceptor.java  # 语言切换拦截器
└── utils/            # 工具类
    ├── JwtUtils.java            # JWT 工具
    ├── I18nUtils.java         # 多语言工具
    └── exceptions/              # 异常处理
```

## 快速开始

### 1. 环境要求

- JDK 21+
- MySQL 8.0+
- Redis 6.0+
- Maven 3.9+

### 2. 数据库配置

修改 `src/main/resources/application.properties`：

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/app
spring.datasource.username=root
spring.datasource.password=123456
```

执行数据库初始化脚本：

```sql
-- 按顺序执行以下脚本（用户表、角色权限表、文件存储表、消息表）
src/main/resources/schema-permission.sql   -- 用户表、角色权限表
src/main/resources/schema-storage.sql       -- 文件存储表
src/main/resources/schema-message.sql       -- 消息记录表、消息模板表
```

### 3. 启动项目

```bash
./mvnw spring-boot:run
```

访问 Swagger UI: http://localhost:8080/swagger-ui.html

## 功能模块

### 1. 用户认证与登录

| 接口 | 方法 | 说明 |
|------|------|------|
| `/auth/register` | POST | 用户注册 |
| `/auth/login` | POST | 用户登录 |

**请求示例：**
```json
{
  "account": "admin",
  "password": "password123"
}
```

**响应示例：**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIs...",
  "userId": 123456789,
  "account": "admin",
  "nickName": "管理员"
}
```

### 2. 用户管理

| 接口 | 方法 | 权限 | 说明 |
|------|------|------|------|
| `/user` | POST | user:create | 创建用户 |
| `/user/{id}` | PUT | user:update | 更新用户 |
| `/user/{id}` | GET | user:read | 获取用户 |
| `/user/{id}` | DELETE | user:delete | 删除用户 |
| `/user/list` | GET | user:read:list | 用户列表 |

### 3. 文件存储

| 接口 | 方法 | 权限 | 说明 |
|------|------|------|------|
| `/storage/upload` | POST | storage:upload | 上传文件 |
| `/storage/{id}` | GET | storage:read | 下载文件 |
| `/storage/{id}/info` | GET | storage:read | 获取文件信息 |
| `/storage/{id}/preview` | GET | storage:read | 预览文件 |
| `/storage/{id}` | DELETE | storage:delete | 删除文件 |
| `/storage/list` | GET | storage:read:list | 文件列表 |

**配置项：**
```properties
storage.local.base-path=/data/uploads
storage.local.max-file-size=10485760
storage.local.allowed-extensions=jpg,jpeg,png,gif,pdf,doc,docx,zip
```

### 4. 消息发送

| 接口 | 方法 | 权限 | 说明 |
|------|------|------|------|
| `/message/send` | POST | message:send | 发送消息 |
| `/message/send/email` | POST | message:send:email | 发送邮件 |
| `/message/send/sms` | POST | message:send:sms | 发送短信 |
| `/message/send/batch` | POST | message:send:batch | 批量发送 |
| `/message/record/{id}` | GET | message:read | 获取发送记录 |
| `/message/records` | GET | message:read:list | 记录列表 |
| `/message/template` | POST | message:template:create | 创建模板 |
| `/message/template/{id}` | GET | message:template:read | 获取模板 |
| `/message/template/{id}` | PUT | message:template:update | 更新模板 |
| `/message/template/{id}` | DELETE | message:template:delete | 删除模板 |

**邮件配置：**
```properties
spring.mail.host=smtp.example.com
spring.mail.port=587
spring.mail.username=noreply@example.com
spring.mail.password=xxx
```

## 权限系统

### 预置角色

| 角色 | 说明 |
|------|------|
| ADMIN | 管理员，拥有所有权限 |
| USER | 普通用户 |

### 权限列表

| 权限码 | 说明 |
|--------|------|
| user:create | 创建用户 |
| user:read | 读取用户 |
| user:read:list | 用户列表 |
| user:update | 更新用户 |
| user:delete | 删除用户 |
| auth:login | 登录 |
| auth:register | 注册 |
| storage:upload | 上传文件 |
| storage:read | 读取文件 |
| storage:delete | 删除文件 |
| message:send | 发送消息 |
| message:read | 读取消息记录 |
| message:template:* | 模板管理 |

### 使用方式

在 Controller 方法上使用 `@PreAuthorize` 注解：

```java
@PreAuthorize("hasAuthority('user:delete') or hasRole('ADMIN')")
@DeleteMapping("{id}")
public void delete(@PathVariable long id) {
    userServices.delete(id);
}
```

## API 认证

### 获取 Token

```bash
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{"account": "admin", "password": "password"}'
```

### 使用 Token

```bash
curl -X GET http://localhost:8080/user/1 \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIs..."
```

## 错误处理

全局异常处理器统一返回错误响应：

```json
{
  "code": 404,
  "message": "用户不存在"
}
```

| HTTP 状态码 | 说明 |
|-------------|------|
| 400 | 参数错误 |
| 401 | 认证失败 |
| 403 | 权限不足 |
| 404 | 资源不存在 |
| 409 | 数据冲突 |
| 500 | 服务器内部错误 |

## 如何使用缓存

### 1. 修改配置文件

```properties
spring.data.redis.host=localhost
spring.data.redis.port=6379
```

### 2. 启用缓存

在 Spring Boot 主应用程序类上添加 `@EnableCaching` 注解：

```java
@SpringBootApplication
@EnableCaching
public class SpringApplicationTemplateApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringApplicationTemplateApplication.class, args);
    }
}
```

### 3. 使用缓存注解

```java
@Service
public class UserService {
    // @Cacheable - 查询时缓存
    @Cacheable(value = "users", key = "#id")
    public UserModel findUserById(long id) {
        // 首次调用会查询数据库，后续从缓存获取
        return userRepository.findById(id);
    }

    // @CacheEvict - 更新时清除缓存
    @CacheEvict(value = "users", key = "#id")
    public void delete(long id) {
        userRepository.delete(id);
    }
}
```

## 多语言支持

### 支持语言

| 语言 | 请求头值 |
|------|----------|
| 中文 | `Accept-Language: zh-CN` |
| 英文 | `Accept-Language: en-US` |

### 语言文件

| 文件 | 说明 |
|------|------|
| `messages.properties` | 默认语言（英文） |
| `messages_zh_CN.properties` | 中文 |

### 使用方式

在代码中调用 `I18nUtils`：

```java
// 简单用法
String msg = I18nUtils.get("user.not.found");

// 带参数
String msg = I18nUtils.get("user.not.found", new Object[]{username});

// 错误处理中使用
@ExceptionHandler(DataNotExistsException.class)
public ResponseEntity<ExceptionDetail> handleException(DataNotExistsException ex) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(new ExceptionDetail(404, I18nUtils.get("data.not.exists")));
}
```

## License

MIT