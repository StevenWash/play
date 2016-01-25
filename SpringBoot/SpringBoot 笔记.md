####创建Maven工程，增加如下依赖
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
	<version>1.2.3.RELEASE</version>
</dependency>
```
####编写一个Controller
```java
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/spring-boot")
public class HelloController {

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    @ResponseBody
    public String hello() {
        System.out.println("Hello, this is spring boot");
        return "Spring Boot";
    }
}
```

####创建启动类
```java
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration    
@ComponentScan("com.demo.spring.boot")
@EnableAutoConfiguration    
public class Startup {  
    public static void main(String[] args) {  
	        SpringApplication app = new SpringApplication(Startup.class);  
	        app.run(args);  
    }    
}
```
启动容器。
通过run application方式运行主函数，即可启动容器。

启动容器后， 打开浏览器，访问：http://localhost:8080/spring-boot/hello  就可以在页面中看到输出“Spring Boot”, 同时可以在控制台看到输出信息：Hello, this is spring boot 。

这样，一个简单的spring-boot环境就搭建好了。
