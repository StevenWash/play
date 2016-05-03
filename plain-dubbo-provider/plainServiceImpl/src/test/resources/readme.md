- 右键项目，确保 `Java Build Path->Source->${projectName}/src/test/resources` 的 `Excluded:(None)` 是这样的，否则编译的时候不会将该文件夹下的文件打到  `${projectName}\target\test-classes` 目录中；

- 测试环境中会将 `${projectName}/src/test/resources/test` 目录下的`properties` 文件通过 `${projectName}/src/test/resources/META-INF/spring/spring-config.xml` 中的 PropertyPlaceholderConfigurer 配置类读取指定的配置；

- 在 `${projectName}/pom.xml` 中通过 `profile` 来区分不同的环境，打包的时候会将不同 `profile`的属性值打到 ${projectName}/src/main/resources 目录下的 `properties` 文件中，通过 `${属性名}` 来匹配。

- 启动 `${projectName}/src/test/java/com/toulezu/service/start/DemoProvider.java` 将会启动本地的dubbo服务；

- 通过 `${projectName}/src/main/resources/bin-assemble-descriptor.xml` 文件中的配置，执行 `clean install -Dmaven.test.skip=true -Pprd` 将项目打包，将包部署到Linux中，解压后执行 bin 目录下的 start.sh 启动项目，执行 `ps -wef | grep ${项目路径}` 查看是否启动；