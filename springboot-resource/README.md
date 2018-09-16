# Resource接口
Spring的Resource接口，提供了更强的底层资源访问能力。Spring框架使用Resource装载各种资源，包括配置文件资源、国际化属性文件资源等。下面是Resource接口定义的方法：

![Aaron Swartz](https://github.com/mistifler/Notes/blob/master/pictures/springboot-ref/springboot-resource/ResourceMethod.png)

org.springframework.core.io包中Resource相关接口和实现类如下图
![Aaron Swartz](https://github.com/mistifler/Notes/blob/master/pictures/springboot-ref/springboot-resource/ResourceInterface.png)

Spring的配置信息可以放置在任何Resource接口可以访问到的地方就可以了，而且访问的方式也很灵活，一些常用的接口或实现类如下：
* WriteableResource 接口：可写资源
* ByteArrayResource：二进制数组表示的资源
* InputStreamResource：以输入流返回表示的资源
* ClassPathResource：类路径下的资源，资源以相对于类路径的方式表示
* UrlResource：URL封装了java.net.URL，通过URL表示的资源，如文件资源(file:协议)、HTTP资源等
* PathResource：封装了java.nio.file.Path，支持文件资源和URL资源
* FileSystemResource：文件系统资源，资源以文件系统路径的方式表示

# 资源加载机制
不同资源类型必须使用相应的Resource实现类，比较麻烦，spring提供了加载资源的机制，可以通过classpath: file: http://  等资源地址前缀来自动进行识别，如果没有前缀，则根据对应类型的Resourceshixianlei 在此基础上，还支持Ant风格带通配符的资源地址。
资源加载器，ResourceLoader仅有一个方法：Resource getResource(String location); 表示根据资源地址加载资源文件。

![Aaron Swartz](https://github.com/mistifler/Notes/blob/master/pictures/springboot-ref/springboot-resource/ResourceLoader.png)
