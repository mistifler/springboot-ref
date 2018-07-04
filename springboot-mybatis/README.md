[TOC]
# Spring boot with Mybatis

Spring boot 集成 Mybatis

Mybatis generator自动生成插件的使用

## 引入依赖
在pom文件中引入mybatis的starter
```
<dependency>
    <groupId>org.mybatis.spring.boot</groupId>
    <artifactId>mybatis-spring-boot-starter</artifactId>
    <version>1.3.2</version>
</dependency>
```

## 使用Mybatis Generator
首先，在pom.xml中引入该插件的依赖
推荐这里配置下JDBC驱动的依赖(需要指定版本)，不然执行的时候可能会报错找不到JDBC Driver
```
<plugin>
    <groupId>org.mybatis.generator</groupId>
    <artifactId>mybatis-generator-maven-plugin</artifactId>
    <version>1.3.5</version>
    <dependencies>
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>5.1.29</version>
        </dependency>
    </dependencies>
</plugin>
```

然后，书写配置文件，具体参考resources/mybatis-generator.xml

最后，执行即可，可以考虑用IDEA的Maven插件。

可能出现的报错：

元素类型为 "context" 的内容必须匹配 "(property*,plugin*,commentGenerator?,(connectionFactory|jdbcConnection),javaTypeResolver?,javaModelGenerator,sqlMapGenerator?,javaClientGenerator?,table+)"

解决：
配置的时候注意各个context里的子标签的顺序

关于mybatis generator的更多配置可以参考: 

http://www.mybatis.org/generator/

http://www.mybatis.org/generator/reference/plugins.html

