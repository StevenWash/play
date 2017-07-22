 ## 项目说明

- 这是一个Maven web项目

- 依赖于像Tomcat的web容器

- 集成了Spring MVC,Swagger,Dubbo,Junit等技术

- 通过Dubbo依赖分布式服务进行数据读取和持久化

- 用于对外提供轻量级HTTP服务

- 使用Spring-Swagger将API文档化，便于API的后期维护，Swagger的静态文件在`src/main/webapp/resources`目录下

- 使用MOCK,Junit对Spring的Controller进行单元测试

- 使用Aspect AOP统一进行日志和权限处理

- 项目启动成功后在浏览器中访问 `http://localhost:8080/webApi/swagger-ui.html`


