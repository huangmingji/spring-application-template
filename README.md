# spring boot项目模板

## 如何使用缓存  

1. 修改配置文件application.properties

``` bash
spring.data.redis.host=localhost
spring.data.redis.port=6379
```

2. 在Spring Boot主应用程序类上添加@EnableCaching注解，以启用缓存功能
``` java
@SpringBootApplication
@EnableCaching
public class SpringApplicationTemplateApplication {
	public static void main(String[] args) {
		SpringApplication.run(SpringApplicationTemplateApplication.class, args);
	}
}
```

3. 在需要缓存的类上添加@CacheConfig注解，并指定缓存的名称，如@Cacheable、@CacheEvict、@CachePut
``` java
@Service
public class UserService {
    //用于标记某个方法的结果应该被缓存。当方法被调用时，首先检查缓存中是否存在对应的键值。如果有，则直接从缓存中获取结果；否则，执行方法并将结果放入缓存。当多次请求同一用户ID时，只有第一次会实际执行查询方法，后续请求将直接从users缓存中获取结果。
    @Cacheable(value = "users", key = "#userId")
    public UserDto getUserById(String userId) {
        // 实际查询逻辑，如从数据库获取用户信息
        // ...
        return userDto;
    }
}

@Service
public class UserService {
    //用于清除缓存中的数据。可以设置在某个方法上，当该方法被调用时触发缓存清除。当updateUser方法被调用时，会清除users缓存中与传入userId对应的数据，确保下次访问时能获取到更新后的用户信息。
    @CacheEvict(value = "users", key = "#userId")
    public void updateUser(String userId, UserDto updatedUser) {
        // 更新用户逻辑...
    }
}

@Service
public class UserService {
    //用于更新缓存而不影响方法的正常执行。通常与@Cacheable一起使用，确保缓存始终与最新数据保持一致。saveUser方法在保存用户的同时，会将返回的savedUser对象以user.id作为键更新到users缓存中。
    @CachePut(value = "users", key = "#user.id")
    public UserDto saveUser(UserDto user) {
        // 保存用户逻辑...
        return savedUser;
    }
}
```
