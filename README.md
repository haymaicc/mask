## 脱敏

### introduction

在信息安全愈来愈重要的今天，用户信息脱敏展示是一个很常见的问题。和用户相关的诸多信息都需要脱敏展示。比如姓名，手机号，身份证，银行卡号…………

### prerequisite
- springboot

### quick start
1. 添加`@EnableMask`
2. 在需要脱敏的method上加`@UseMask`
3. 在方法的返回结果类中使用如下注解标记field
    - `@MobileMask`
    - `@IdCardMask`
    - `@BankAccountMask`
    - `@NameMask`

注意：支持嵌套递归

### demo

```java
@EnableMask
@SpringBootApplication
public class DemoApplication{
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
}
```
 
```java
@RestController
public class UserController {
    @UseMask
    @GetMapping("/user/{id}")
    public User getUser(@PathVariable("id") String id) {
        return getUser();
    }
    
    @UseMask
    @GetMapping("/user")
    public List<User> getUsers() {
        List<User> users = new ArrayList<>();
        users.add(getUser());
        return users;
    }

    private User getUser() {
        User user = new User();
        user.setName("哈哈");
        user.setMobile("13388889999");
        user.setIdCard("333444192112139981");
        user.setBankAccount("12345678901234567");
        return user;
    }

}
```
 
```java
public class User {

    private String name;
    @IdCardMask
    private String idCard;
    @MobileMask
    private String mobile;
    @BankAccountMask
    private String bankAccount;

    //getter setter
}
```

### advanced

#### 根据权限判断是否需要脱敏

1. 实现`MaskPolicy`
```java
@Component
public class WhiteListPolicy implements MaskPolicy {
    //request 是@RequestScope bean每次请求都会不一样
    @Autowired
    private HttpServletRequest request;
    //白名单，可以从配置文件或数据库读取
    private Set<String> whiteList = Sets.newHashSet("majian");

    @Override
    public boolean needMask() {
        UserDepInfo userInfo = UserUtil.getUserInfo(request);
        return !whiteList.contains(userInfo.getAccount());
    }
}
```

2. 覆盖默认配置
```java
@Configuration
public class MaskConfig {
    @Autowired
    private WhiteListPolicy whiteListPolicy;
    //覆盖默认的MaskAdvice配置
    @Bean
    public MaskAdvice maskAdvice() {
        MaskAdvice maskAdvice = new MaskAdvice();
        maskAdvice.setMaskPolicy(whiteListPolicy);
        return maskAdvice;
    }
}
```

#### 自定义脱敏策略
如果不想用默认的手机号脱敏（137****9988）,可以继承 `MobileMasker` 并`override` `doMask()`方法，自定义配置会自动覆盖默认配置。
```java
@Configuration
public class MaskConfig {
        
    @Bean
    public MobileMasker mobileMasker() {
        return new MobileMasker() {
            @Override
            protected String doMask(String content) {
                return "mymobilemask";
            }
        };
    }
}
```
其它身份证，银行卡神马的同理。

### 默认配置

- `MaskPolicy`: 对所有人都脱敏
- `MobileMasker`: 134****9999, 4-7位
- `IdCardMasker`: 出生年月
    - 18位:330777********667X
    - 15位:330777******667
- `BankAccountMasker`: 622619****063582，7-10位
- `NameMasker`: 张三*（对最后一个字脱敏）

## 重要！！！！
如果输入对数据格式不正确（eg. 16为身份证），会作如下处理：
1. 打印异常日志（MaskException）
2. 全部脱敏（****************）

**所以如果有看到全部脱敏的数据，请返回查看日志**

---
**为避免无穷递归，规定最深层级为10层。如果超过10层，则抛异常`RecursiveTooDeepException`并结束线程**

