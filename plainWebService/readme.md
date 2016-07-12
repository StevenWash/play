# 项目说明

## 一个简单的基于Maven构建的Java Web项目，使用SpringBoot框架，通过`profile`可以在打包的时候区分不同环境的配置，不需要应用服务器即可启动

## 项目结构

- `com.test.bean` 业务实体

- `com.test.controller`  Conroller

- `com.test.run` 启动类

## 启动

- 执行 `/plainWebService/src/main/java/com/test/run/Run.java` 即可启动项目

- 在浏览器中输入 `http://localhost:8080/test/hello` 即可访问在Conroller `/plainWebService/src/main/java/com/test/controller/TestConroller.java` 中编写的逻辑

## 开发微服务

- Boot对Spring应用的开发进行了简化，提供了模块化方式导入依赖的能力

- 强调了开发RESTful Web服务的功能并提供了生成可运行jar的能力，让一个RESTful Web工程运行起来是一件很容易的事情

## 数据访问

- 只需在你的工程中将`spring-boot-starter-data-jpa`包含进来，Boot的自动配置引擎就能探测到你的工程需要数据访问功能，并且会在Spring应用上下文中创建必要的Bean，这样你就可以使用Repository和服务了。

## 参考

[深入学习微框架：Spring Boot](http://www.infoq.com/cn/articles/microframeworks1-spring-boot)
