Spring Boot 特点

Spring boot 可以创建一个独立的应用程序；
嵌入了tomcat，Jetty容器，同时支持不使用第三方容器，直接启动应用程序；
 为maven 工程提供简单依赖配置项 (spring-boot-starter-web)；
尽可能的支持自动配置；
提供更完善的功能特性：如： metrics, health checks 和 externalized configuration。
绝对没有代码生成和XML配置的要求，可以完全脱离XML配置。

通过run application方式运行主函数，即可启动容器。

启动容器后， 打开浏览器，访问：http://localhost:8080/spring-boot/hello  就可以在页面中看到输出“Spring Boot”, 同时可以在控制台看到输出信息：Hello, this is spring boot 。