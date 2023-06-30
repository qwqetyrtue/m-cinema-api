# 电影院网上购票系统开发

## 功能

### 权限管理

|      | 超级管理员 | 用户管理员 | 电影管理员 | 文章管理员 | 评论管理员 |
| ---- | ---------- | ---------- | ---------- | ---------- | ---------- |
|      |            |            |            |            |            |
|      |            |            |            |            |            |
|      |            |            |            |            |            |
|      |            |            |            |            |            |
|      |            |            |            |            |            |

## API文档

[管理员后台-API文档](管理员后台api文档.md)

## mybatis-generate

项目的resource和java其实在运行生成target后会合并到一个文件夹里,所以使用mybatis-generator的时候配置生成的 xxxmapper.java和 xxxmapper.xml分别放在 java和resource中的同样的目录里,这样打包后将放在同一个目录下

![image-20230218080801831](./resources/image-20230218080801831.png)



## maven依赖管理

maven子项目怎么只使用部分父项目的依赖: 父项目中,将非公用的依赖添加到` <dependenciesManagement/>`中,这里面的依赖,需要子项目手动的在项目依赖中添加,才会生效,且不需要添加依赖版本,依赖版本默认跟父项目一致

![image-20230218162034199](./resources/image-20230218162034199.png)

## 用户识别认证

[SpringBoot中使用Shiro和JWT做认证和鉴权(一)](https://blog.csdn.net/m0_37647376/article/details/100038503)

[shiro(8)-SessionManager(单处登录)](https://www.jianshu.com/p/0e659051463f)

[你还在用JWT做身份认证嘛？](https://zhuanlan.zhihu.com/p/449864222)

## shiro

### 三大对象

+ Subject 当前用户
+ S ecurityManageMent 管理所有用户
+ Reaml 连接数据

### 配置

```java

/**
 * <ul>
 * <li>anon: 无需认证就能访问
 * <li>authc: 必须认证才能访问
 * <li>user: 开启记住我功能才能用
 * <li>perms: 拥有对某个资源的权限才能访问
 * <li>role: 拥有某个角色权限才能访问
 * </ul>
 */
@Configuration
public class AdminShiroConfig {
    /**
     * <h3>注册shiroFilterFactoryBean</h3>
     *
     * @param securityManager
     * @return
     */
    @Bean(name = "shiroFilterFactoryBean")
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        bean.setSecurityManager(securityManager);
        Map<String, String> filter = new LinkedHashMap<>();
		filter.put("/admin/auth/login", "anon");
		filter.put("/admin/auth/401", "anon");
        // 设置拦截器
        bean.setFilterChainDefinitionMap(filter);
        return bean;
    }


    /**
     * <h3>注册DefaultWebSecurityManager</h3>
     *
     * @param adminRealm realm
     * @return securityManager
     */
    @Bean(name = "securityManager")
    public DefaultWebSecurityManager adminWebSecurityManager(AdminAuthorizingRealm adminRealm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(adminRealm); // 设置Realm对象
        return securityManager;
    }

    /**
     * <h3>注册Realm</h3>
     *
     * @return realm对象
     */
    @Bean(name = "adminRealm")
    public AdminAuthorizingRealm adminRealm() {
        return new AdminAuthorizingRealm();
    }



    /**
     * <h3>开启注解功能</h3>
     * <a href="https://blog.csdn.net/hjseo_seg/article/details/126496478">xml的配置</a>
     * <p>
     * 开启Shiro的注解(如@RequiresRoles,@RequiresPermissions),
     * 需借助SpringAOP扫描使用Shiro注解的类,并在必要时进行安全逻辑验证 *
     * 配置以下两个bean(DefaultAdvisorAutoProxyCreator(可选)
     * 和AuthorizationAttributeSourceAdvisor)即可实现此功能
     * </p>
     * @return
     */
    @Bean
    @DependsOn("lifecycleBeanPostProcessor")
    public static DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator creator = new DefaultAdvisorAutoProxyCreator();
        creator.setProxyTargetClass(true);
        return creator;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }
}
```

>要配置的选项就3个
>
>+ realm
>+ DefaultWebSecurityManager
>+ shiroFilterFactoryBean 在这里面配置拦截器和页面登录跳转(前后端分离不要配页面跳转)


### 使用

```java
@RequestMapping(value = "/login",method = RequestMethod.POST)
public HttpRes adminLoginHandle(@RequestBody @Validated AdminLogin login){
    Subject user = SecurityUtils.getSubject();
    UsernamePasswordToken token = new UsernamePasswordToken(login.getUsername(),login.getPassword());
    try{
        user.login(token);
        return HttpResUtil.succeed("登陆成功");
    }catch (UnknownAccountException e){
        return HttpResUtil.fail(HttpResErrorEnum.INVALID_ACCOUNT,"账号或密码错误");
    }catch (AuthenticationException e){
        return HttpResUtil.fail(HttpResErrorEnum.UNKNOWN_EXCEPTION, "认证失败");
    }catch (Exception e){
        return HttpResUtil.fail(HttpResErrorEnum.UNKNOWN_EXCEPTION, "未知错误");
    }
}
```

> 调用Subject中的login方法,则会进入到代理流程中,调用realm中的`doGetAuthenticationInfo`方法执行里面的逻辑,再返回一个`AuthenticationInfo`对象,此对象不能为空,否则会报错,对象包含[当前用户],[验证令牌(密码)],[realm的`className`]
>
> 在`controller`中只需要调用subject.login方法,并捕获抛出的错误,没有错误既通过了登录验证逻辑,这时候用`SecurityUtils.getSubject().getPrincipal()`可用获取当前用户存入的信息,也就是前面提到的返回对象中[当前用户]属性里面的内容

### 错误统一处理

> 配置统一处理认证和授权错误,这样未登录被拦截的请求将不会默认跳转到500页面,而是走处理中的逻辑

```java
@ControllerAdvice
@Order(value = Ordered.HIGHEST_PRECEDENCE)
public class ShiroExceptionHandle {
    private final Log logger = LogFactory.getLog(ShiroExceptionHandle.class);

    /**
     * <h3>配置统一处理未认证错误</h3>
     * <p>
     *     否则默认跳转到500页面
     * </p>
     * @param e
     * @return
     */
    @ExceptionHandler(AuthenticationException.class)
    @ResponseBody
    public HttpRes unauthenticatedHandler(AuthenticationException e) {
        logger.warn(e.getMessage(), e);
        return HttpResUtil.unLogin();
    }

    /**
     * <h3>配置统一处理授权错误</h3>
     * <p>
     *
     * </p>
     * @param e
     * @return
     */
    @ExceptionHandler(AuthorizationException.class)
    @ResponseBody
    public HttpRes unauthorizedHandler(AuthorizationException e) {
        logger.warn(e.getMessage(), e);
        return HttpResUtil.unAuth();
    }

}

```

## JWT `Json web Token`

[ JWT菜鸟教程](http://www.codebaoku.com/jwt/jwt-index.html)

###  JWT 组成结构

JWT 的 token 由三段小数点分隔组成的字符串：`header.payload.signature`。

#### 1. JWT header

头部包含两部分：声明类型和使用的哈希算法，通常直接使用HMAC SHA256，就是HS256。

```
{
    "typ": "JWT",
    "alg": "HS256"
}
```

将头部进行 Base64 编码构成第一部分。Base64 是一种用 64 个字符来表示任意二进制数据的方法，常用于在 URL、Cookie、网页中传输少量二进制数据。

 

#### 2. JWT payload

也称为JWT claims，放置需要传输的信息，有三类：

- 保留claims，主要包括iss发行者、exp过期时间、sub主题、aud用户等。
- 公共claims，定义新创的信息，比如用户信息和其他重要信息。
- 私有claims，用于发布者和消费者都同意以私有的方式使用的信息。

JWT规定7个官方字段，供选用：

- iss (issuer)：签发人。
- exp (expiration time)：过期时间。
- sub (subject)：主题。
- aud (audience)：受众。
- nbf (Not Before)：生效时间。
- iat (Issued At)：签发时间。
- jti (JWT ID)：编号。

支持定义私有字段，示例：

```
{
    "iss": "jwt.io",
    "exp": 1496199995458,
    "name": "sinwaj",
    "role": "admin"," 
}
```

JWT 默认是不加密的，任何人都可以读到，所以不要把秘密信息放在这个部分。

 

#### 3. JWT signature

生成签名部分，需要准备：编码的 header、编码的 payload 和一个秘钥，签名算法是 header 指定的算法，然对这些数据进行签名。

签名公式如下：

```
HMACSHA256(base64UrlEncode(header) + "." + base64UrlEncode(payload), secret)
```

其中：secret 为加密使用的盐，也可以认为是私钥，千万不能泄露。

 

#### 4. 完整的 JWT

将 header、payload 和 signature 三部分用 . 连接成一个完整的字符串，构成了最终的 JWT：

例如：

```
eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiYWRtaW4iOnRydWV9.TJVA95OrM7E2cBab30RMHrHDcEfxjoYZgeFONFh7HgQ
```

其中：

- eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9 是 Base64 编码的 header。
- eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiYWRtaW4iOnRydWV9 是 Base64 编码的 payload。
- TJVA95OrM7E2cBab30RMHrHDcEfxjoYZgeFONFh7HgQ 是对 header、payload 的签名。

注意：secret 保存在服务器端，JWT 的生成也在服务器端，secret 用来进行 jwt 的签发和 jwt 的验证，它就是服务端的私钥。

所以，在任何场景下都不应该泄露出去。一旦客户端得知这个 secret，那就意味着客户端是可以自我签发 jwt。




## Spring bean

### 生命周期

```mermaid
---
title: bean的生命周期
---
flowchart LR
    1[class]-->1.5[推断构造方法]-->2[实例化]-->2.5[实例化对象]-->3["属性填充(依赖注入)"]-->4["初始化(调用afterPropertiesSet)"]--进行AOP-->5[AOP]-->7[代理对象]-->6[bean];
    4--不进行AOP-->6;
```



#### spring是怎么初始化对象的(构造方法推断)

##### 构造方法的选择

+ 一个构造方法,直接用
+ 多个构造方法,没有无参构造方法,报错
+ 多个构造方法,有无参构造方法,选无参构造方法
+ 添加@Autowired注解告诉spring用哪个方法

##### 构造方法填充参数

```java
@componet
public class UserService {
	public UserService(OrderService orderService){
        
    }
}
```

> 这里`UserService`构造方法中含参数`OrderService` 这个参数spring是从哪来取来的
>
> >  byType --> byName
>
> ##### 1.spring先通过类名,在注册的bean中查找对应的类型
>
> `单例bean和单例模式(不是一个东西)`
>
> > singletonScope与prototypeScope
>
> ​	[Spring的单例bean与原型bean](https://www.jianshu.com/p/36a065065e3c)
>
> + 单例bean, 具有单例模式的所有特性,在获取bean时,多次获取的bean是同一个的是单例bean
> + 原型bean,每次获取到的都是一个新的实例
>
> 这说明在spring注册的bean中可能出现相同类型的不同实例的bean
>
> ##### 2.如果有一个bean则直接当参数传入
>
> ##### 3.如果有多个bean,再通过属性名,查找前面通过类名查找到的实例,如果有则直接当作参数传入,没有报错



#### 初始化

```java
@Component //注册依赖的注解
public class UserService() implements InitializingBean{ 
    // 实现spring提供的需要初始化参数的bean接口,这个接口提供了一个afterPropertiesSet方法
    // 用于在bean完成属性填充后 初始化没有注册为依赖的属性的值,像这里的User类
    // User类没有注册到Spring中,像要给他一个初始值,一种实现方法就是实现InitializingBean接口
    @Autowired //依赖注入
    OrderService orderService;
    
    private User defaultUser;
    
    @Override
    public void afterPropertiesSet() throws Exception{
        // 查询数据库设置默认用户
    }
    
    // 也可以用@postConstruct注解 通知spring这个方法是初始化方法
    @postConstruct
    public void mInit(){
        
    }
}

@Component
public class OrderService(){
    
}

public Class User(){
    
}
```

#### 依赖注入(属性填充)

##### @Autowired

```mermaid
---
title: name
---
flowchart LR
1[byType] --> 2[过滤非候选] --> 3["Qualifer筛选(分组/别名)"] --1.设置有主bean--> 4["获取@Primary注解的主bean(设置了多个主bean会报错)"];
3--2.设置了优先级-->5[优先级的最高的bean]
3--3.都没有设置--> 6[byName]
```



> `@Bean(AutowireCandidate = false)` 意思是将这个bean从候选中剔除,当spring byType找到所有对应类的实例后,还会进行非候选实例的筛选

##### @Resource

> byName-->byType 

+ @Resouce(name = "XXX") 写了name属性 直接用name找bean
+ 没写name属性
  + 用属性名判断有无同名的bean
  + 没有同名的bean,再用类型找





#### AOP代理

> + cglib 基于父子类的实现,生成一个对应的XxxxProxy类继承Xxxx类,覆写父类方法要切面的方法,在方法中调用before,after对应的切面方法
>
>   代理对象没有进行属性填充,所有里面的属性是为空的,但是又一个target对象,这个对应最开始实例化的对象,他是进行了属性填充的
>
>   ```java
>   @Aspect
>   @Component
>   public class UserTestAspect{
>       @Before("excuteion(public void com.UserService.test())")
>       public void testBefore(){
>           // testBefore ......
>       }
>   }
>                               
>   // 被切片的类
>   class UserService(){
>       public void test(){
>                                       
>       }
>   }
>                               
>   // cglib生成的代理类
>   class UserServiceProxy extend UserService {
>       UserService target
>                                   
>       @override
>       public void test(){
>           userTestAspect.testBefor();
>           target.test();
>       }
>   }
>   ```

## Spring 事务

在数据库直接对数据库进行操作时,每条sql语句都是一个事务,默认情况,数据库的操作都开启了autoCommit,也就是执行一条commit一条

正常情况应该是

```sql
begin
# -----
# 增删改查语句
# -----
commit
```

而spring真是利用了这点,将DataBaseConnect.isAutoCommit = false,设置数据库事务非自动提交

这样使用aop操作后,如果catch到错误调则放弃提交,回滚数据库

## @Configuration注解

```java
@Configuration
class AppConfig{
    @Bean
    public appA(){
        A a = appA();
        return new A();
    }
    
    @Bean
    public appB(){
        C c = appC();
        return new B()
    }
    
    @Bean
    public appC(){
        return new C();
    }
}
```

> 加了Configuration后,appConfig会生成代理类,这时候 appA()返回的就是同一个对象,调用时,会先找bean里面有没有appA的bean,有就直接用,没有就注册

## 验证码 `kaptcha`




## 注解

### 本质

继承Annotation接口的接口

### 作用

+ 注解，Annotation是JDK5.0引入的新技术。
+ 注解的格式：@注释名，还可以添加参数（必要时）
+ 注解不是程序本身，但可以对程序作出解释（就这一点，注释和注解的作用类似）
+ 注解可以被其他程序读取（比如编译器等等）
+ 注解可以给Java包、类型(类、接口、枚举)、构造器、方法、域、参数和局部变量进行注解，相当于给它们添加了一些额外的辅助信息。Java编译器可以根据指令来解释注解和放弃注解，或者将注解放到编译后的生成的class文件中，运行时可用。通过反射机制编程实现对这些元数据的访问。

一个注解准确意义上来说，只不过是一种特殊的注释而已，如果没有解析它的代码，它可能连注释都不如。注解只是元数据，不包含任何业务逻辑。


### 元注解

![img](./resources/c0c498405dbcfed37690abd41b8a9ffa.png)

### 定义一个注解

```java
// 注解的对象
@Target({ElementType.METHOD})
// 注解的生命周期
@Retention(RetentionPolicy.RUNTIME)
public @interface MyAnnotation {
    String name;
    int age default 11; 
}
```

## Swagger2

+ api接口文档
+ 前后端分离

### 导入

```xml
```

### 配置Swagger2

```java
@Configuration
@EnableSwagger2
public class AdminSwagger2Config {

    @Bean(name = "adminDocket")
    public Docket getDocket(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(getAdminApiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("cn.hnist.sharo.mcinema.adminapi.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo getAdminApiInfo(){
        Contact contact = new Contact("sharo","https://github.com/qwqetyrtue","1213564573@qq.com");
        return new ApiInfo(
                "电影院网上购票系统-Admin",
                "电影院网上购票系统管理员后台接口文档",
                "1.0",
                "https://github.com/qwqetyrtue",
                contact,
                "Apache 2.0",
                "http://www.apache.org/licenses/LICENSE-2.0",
                new ArrayList());

    }
}
```

### knife4j 页面增强插件配置 [文档](https://doc.xiaominfo.com/docs)

```yaml
knife4j:
  enable: true
  openapi:
    title: Knife4j官方文档
    description: "`我是测试`,**你知道吗**"
    email: xiaoymin@foxmail.com
    concat: 八一菜刀
    url: https://docs.xiaominfo.com
    version: v4.0
    license: Apache 2.0
    license-url: https://stackoverflow.com/
    terms-of-service-url: https://stackoverflow.com/
    group:
      group1:
        group-name: 默认
        api-rule: package
        api-rule-resources:
          - cn.hnist.sharo.mcinema.adminapi.controller
```

### 访问Swagger2的api页面

> [swagger2原始页面 `host:port/swagger-ui.html`](localhost:8080/swagger-ui.html)
>
> [页面增强插件 `host:port/doc.html`](localhost:8080/doc.html)


## 任务

### 异步任务



### 定时任务

```mermaid
flowchart TD;
ScheduleRegister --添加,删除--> CornTask["CornTask(spring)"];
CornTask --包含--> Task & Time;
Task --> MTask
```



### 邮件发送

## IP存储

参考: [Spring 客户端 IP 地址获取及存储细节](https://www.ituring.com.cn/article/510334)

```java
public class IPUtil {
    // 含IP的请求头IP
    private static final String[] IP_HEADER_CANDIDATES = {
            "X-Forwarded-For",
            "Proxy-Client-IP",
            "WL-Proxy-Client-IP",
            "HTTP_X_FORWARDED_FOR",
            "HTTP_X_FORWARDED",
            "HTTP_X_CLUSTER_CLIENT_IP",
            "HTTP_CLIENT_IP",
            "HTTP_FORWARDED_FOR",
            "HTTP_FORWARDED",
            "HTTP_VIA",
            "REMOTE_ADDR"
    };

    private static final Logger logger = LoggerFactory.getLogger(IPUtil.class);

    public static String getIPAddress(NativeWebRequest webRequest) {
        // 提取header得到IP地址列表（多重代理场景），取第一个IP
        for (String header : IP_HEADER_CANDIDATES) {
            String ipList = webRequest.getHeader(header);
            if (ipList != null && ipList.length() != 0 &&
                    !"unknown".equalsIgnoreCase(ipList)) {
                return ipList.split(",")[0];
            }
        }

        // 没有经过代理或者SLB，直接 getRemoteAddr 方法获取IP
        String ip = ((ServletRequest) webRequest.getNativeRequest()).getRemoteAddr();

        // 如果是本地环回IP，则根据网卡取本机配置的IP
        if ("127.0.0.1".equals(ip) || "0:0:0:0:0:0:0:1".equals(ip)) {
            try {
                InetAddress inetAddress = InetAddress.getLocalHost();
                return inetAddress.getHostAddress();
            } catch (UnknownHostException e) {
                e.printStackTrace();
                return ip;
            }
        }
        // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        if (ip != null && ip.length() > 15) { // "***.***.***.***".length()
            // = 15
            if (ip.indexOf(",") > 0) {
                ip = ip.substring(0, ip.indexOf(","));
            }
        }
        return ip;
    }
}
```

## AOP

[SpringBoot中的AOP使用](https://blog.csdn.net/weixin_45583303/article/details/118565966)

### Signature

[AOP之JoinPoint.getSignature()](https://blog.csdn.net/weixin_44783506/article/details/125136847)

```java
joinPoint.getSignature().toString():String com.fast.web.controller.TestController.getString()
joinPoint.getSignature().toShortString():TestController.getString()
joinPoint.getSignature().toLongString():public java.lang.String com.fast.web.controller.TestController.getString()
joinPoint.getSignature().getName():getString
joinPoint.getSignature().getModifiers():1
joinPoint.getSignature().getDeclaringType():class com.fast.web.controller.TestController
joinPoint.getSignature().getDeclaringTypeName():com.fast.controller.TestController
```

### 加强注解时怎么获取注解中的参数

[spring boot aop 获取注解上的参数值](https://www.cnblogs.com/syrgdm/p/13602913.html)

````java
/**获取目标方法上@ReqiredLog注解中定义的operation名字*/
```
private String getOperation(ProceedingJoinPoint jp)throws Exception {
//1.获取目标对象类型
Class<?> targetCls=jp.getTarget().getClass();
//2.获取目标方法对象
//2.1获取方法签名信息
MethodSignature ms=(MethodSignature)jp.getSignature();
//2.2获取方法对象
//假如底层配置为jdk代理,则method指向接口中的抽象方法对象.
//假如底层配置为CGLIB代理,则这个method指向具体目标对象中的方法对象
//Method method=ms.getMethod();
//假如希望无论是jdk代理还是cglib代理,我们让method变量指向的都是目标对象中的方法对象,那如何实现?
Method method=targetCls.getDeclaredMethod(ms.getName(), ms.getParameterTypes());
//3.获取方法上的reqiredLog注解对象
RequiredLog requiredLog=method.getAnnotation(RequiredLog.class);
//4.获取注解中的operation的值.
if(requiredLog==null)return "operation";
return requiredLog.operation();
}
````

## redis

#### 导入

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-redis</artifactId>
    <version></version>
</dependency>
```

> 说明: 在springBoot2.x以后,原来使用的jedis被替换成了lettuce
>
> jedis: 采用直连,多个线程操作是不安全的,解决办法就是使用jedis pool 连接池!  `BIO模式`
>
> lettuce: 采用netty,实例可用在多个线程中共享,是线程安全的,可以减少线程数据  `NIO模式`

### 常用命令

> + `redis-cli -h [host] -p [port] -a [password]` 连接redis数据库
> + `select [0~16]` 选择数据库
> + `flushall` 清空全部数据库
> + `flushdb` 清空当前数据库
> + `keys *` 获取全部的key集合
> + `get [name]` / `set [name] [value]` 获取值和设置KV对 `set`会覆盖原始值 
>   + `set [name]:[ID]:[properties] [value]` 设置name下的ID对应元素的的属性的值
> + `exists [name]` 判断KV对是否存在
> + `move [name] [db]` 移除`db`中的name的KV对KV
> + `expire [name] [time]` 设置name KV对过期时间 单位是秒
> + `ttl [name]` 获取剩余过期时间
> + `type [name]` 查看KV对类型
> + `append` [name] [string] 拼接新的内容到KV对中 不存在就相当于 `set [name]`
> + `strlen [name]` 获取字符串长度
> + `incr [name]` 累加 `decr [name]` 累减
> + `incrby [name] [number]` 设置累加间隔 同理 `decrby [name] [number]`
> + `getrange [name] [start] [end]` 字符串截取`[start,end]`
> + `setrange [name] [offset] [value]` 字符串替换 从offset开始将后面等长度的字符串替换为value
> + `setex [naem] [seconds] [value]` 设置值和过期时间
> + `setne [name] [value]` 不存在就设置
> + `mset [key] [value] [...key value]` 批量设置
> + `mget [key] [...key]` 批量获取
> + `msetnx [key] [value] [...key value]` 如果不存在批量设置,``mset`是一个原子操作只有全部成功才能全部设置,不然就是全部不能设置
> + `getset [name] value` 获取后再设置



<iframe src="https://redis.io/commands/" height="400" scrolling="no" border="0" frameborder="no" framespacing="0" allowfullscreen="true"> </iframe>

### 关于redis

+ redis是基于内存操作的,是`单线程的`,CUP不是redis的性能瓶颈,机器的内存和网络带宽才是
+ redis是c语言编写的

## 踩坑

### 用继承VO类的方式接收参数

> 这样可一使用spring的数据校验功能,但是如果覆写了属性,而没有覆写get和set则这时候子类(也就是用来接口接收参数的类)的get和set设置的都是父类中private的属性,在子类里无法获取,这回导致数据验证一种不通过
>
> 例如:
>
> ```java
> // 这是由 mybatis-generate生成的vo类
> 
> public class AdminBase {
>     public static final Boolean IS_DELETED = Deleted.IS_DELETED.value();
>     public static final Boolean NOT_DELETED = Deleted.NOT_DELETED.value();
>     private Integer adminId;
>     private String name;
>     private String account;
>     private String phone;
>     private String pwd;
>     private LocalDateTime createTime;
>     private LocalDateTime updateTime;
>     private LocalDateTime lastLoginTime;
>     private String lastLoginIp;
>     private Boolean deleted;
>     private String avater;
>     private Integer[] roleIdList;
>         this.createTime = createTime;
>     }
> 	// 后面省略get set 还有其他自动生成的方法
> }
> ```
>
> 
>
> ```java
> public class AdminRec extends AdminBase {
>     private String name
> }
> 
> // 这里继承上面的类,但是setName getName操作的都是父类的name,子类永远未null
> ```
>



### shiro使用reids缓存session

+ 方案一: shiro-redis插件[未成功]

  +  [shiro整合redis出现java.lang.ClassNotFoundException: org.apache.shiro.event.EventBus异常](https://www.likecs.com/show-203749221.html)
  + [java.io.NotSerializableException](https://blog.csdn.net/qq_36396104/article/details/84194799)

+ 方案二: 修改sessionDao,手动实现存储读取

  + 出现序列化问题: 创建有个新的redisTemplate,设置序列化为byte[]

    + [shiro继承redis序列化失败-----坑](https://blog.csdn.net/weixin_43549578/article/details/84821986#commentBox)
    + 成功实现存储和读取

    + 不足: 不能在redis清除的看到存储的session信息

  + 存储mybatis-generator生成的pojo对象无法序列化

    + 给pojo生成时配置mybatis-genrerate插件使实现Serializable接口
    + [MYBATIS GENERATOR系列(六)----MYBATIS GENERATOR PLUGIN插件之SERIALIZABLEPLUGIN](https://www.freesion.com/article/2687761842/)
    + [Mybatis逆向工程的pojo实现序列化接口的代码](https://www.cnblogs.com/chenmingjun/p/9934842.html)

  + 读取session,shiro多次调用redis读取session

    + [shiro+redis多次调用doReadSession方法的解决方案](https://www.bbsmax.com/A/A7zgwAWnz4/)
    + [解决 springboot整合shiro，redis缓存session 多次从redis获取session问题](https://blog.csdn.net/lianyuecheng/article/details/81663269/)

### 热部署模块导致同类型出现ClassCastException

[SpringBoot项目出现java.lang.ClassCastException的Java类型转换异常解决方案](https://blog.csdn.net/qq_41322460/article/details/101062073)


### 关闭cookie的http-only才能在页面用cookie.js读取和移除

```java
public AdminSessionManagement(String session_token_key, Long session_timeout) {
    this.SESSION_TOKEN_KEY = session_token_key;
    this.SESSION_TIMEOUT = session_timeout;
    this.setGlobalSessionTimeout(SESSION_TIMEOUT);
    SimpleCookie mCookie = new SimpleCookie(SESSION_TOKEN_KEY);
    mCookie.setHttpOnly(false); // 关闭cookie的http-only,使客户端可以读取
    this.setSessionIdCookie(mCookie);
}
```


### DefaultSessionManage.setGlobalSessionTimeout(SESSION_TIMEOUT)这里是以毫秒为单位的

```java
/**
 * Sets the system-wide default time in milliseconds that any session may remain idle before expiring. This
 * value is the main default for all sessions and may be overridden on a <em>per-session</em> basis by calling
 * {@code Subject.getSession().}{@link Session#setTimeout setTimeout(long)} if so desired.
 * <p/>
 * <ul>
 * <li>A negative return value means sessions never expire.</li>
 * <li>A non-negative return value (0 or greater) means session timeout will occur as expected.</li>
 * </ul>
 * <p/>
 * Unless overridden by calling this method, the default value is {@link #DEFAULT_GLOBAL_SESSION_TIMEOUT}.
 *
 * @param globalSessionTimeout the time in milliseconds that any session may remain idle before expiring.
 */
public void setGlobalSessionTimeout(long globalSessionTimeout) {
    this.globalSessionTimeout = globalSessionTimeout;
}
```

