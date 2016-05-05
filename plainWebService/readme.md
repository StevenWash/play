# 一个简单的基于Maven构建的Java Web项目，使用SpringBoot框架，通过`profile`可以在打包的时候区分不同环境的配置
- Maven
- Java Web
- SpringBoot

##项目结构
- `com.test.bean` 业务实体
- `com.test.controller`  Conroller
- `com.test.run` 启动类

##启动
执行 `/plainWebService/src/main/java/com/test/run/Run.java` 即可启动项目，在浏览器中输入 `http://localhost:8080/test/hello` 即可访问在Conroller `/plainWebService/src/main/java/com/test/controller/TestConroller.java` 中编写的逻辑

##开发微服务
Boot对Spring应用的开发进行了简化，提供了模块化方式导入依赖的能力，强调了开发RESTful Web服务的功能并提供了生成可运行jar的能力，这一切都清晰地表明在开发可部署的微服务方面Boot框架是一个强大的工具。正如前面的例子所示，借助于Boot，让一个RESTful Web工程运行起来是一件很容易的事情；不过，为了了解Boot所有潜在的功能，我们会阐述在开发完整功能的微服务时，会遇到的所有繁琐的事情。在企业级基础设施领域，微服务是一种越来越流行的应用架构，因为它能够实现快速开发、更小的代码库、企业级集成以及模块化部署。有众多的框架致力于该领域的开发。

##数据访问
Spring Boot使数据库集成变成了一项非常简单的任务，因为它具有自动配置Spring Data以访问数据库的能力。只需在你的工程中将spring-boot-starter-data-jpa包含进来，Boot的自动配置引擎就能探测到你的工程需要数据访问功能，并且会在Spring应用上下文中创建必要的Bean，这样你就可以使用Repository和服务了。


##参考
[深入学习微框架：Spring Boot](http://www.infoq.com/cn/articles/microframeworks1-spring-boot)



