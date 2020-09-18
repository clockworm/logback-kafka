# logback-kafka
logback结合kafka实现日志推送,该项目抽离成module,方便以jar包的形式,进行依赖管理
即下即用,简单配置.

# 使用方法详见如下:
### 第一步:
###### 需要日志推送的服务 pom.xml添加依赖:
   ```  
        <dependency>
            <groupId>io.github.clockworm</groupId>
            <artifactId>logback-kafka</artifactId>
            <version>0.0.1</version>
        </dependency>
   ```
#
### 第二步:
###### application.properties 添加属性:
        io.github.clockworm.middle.logback.project-group-name=项目组名          ######(项目组名 比如:cbest-bee)
        io.github.clockworm.middle.logback.project-app-name=服务名              ######(服务名  比如:gateway)
        io.github.clockworm.middle.logback.kafka-servers=xxx.xxx.xxx.xxx:9092   ######(kafka地址:端口)
  ![#f03c15](https://placehold.it/15/f03c15/000000?text=+) `注: 以上赋值不允许带下划线或其他特殊符号 错误赋值示例: _+!@#$%^&*()`
### 第三步:
######   通过maven管理查看源码打开logback-kafka包的logback-kafka.xml配置文件:
         Maven Dependencies
         |-- xx.jar
         |-- xx.jar
         `-- logback-kafka-x.x.x.jar
             |-- io.github.clockworm
             |-- META-INF
             `-- logback-kafka.xml
######   将logback-kafka.xml拷贝至自身服务的src/main/resources/目录下:
     `src/main/resources
         `-- application.properties
         `-- logback.xml
         `-- logback-kafka-x.x.x.jar
### 第四步:
######   自身服务的logback.xml或logback-spring.xml,引入logback-kafka.xml:
       <?xml version="1.0" encoding="UTF-8" ?>
       <!DOCTYPE XML>
       <configuration debug="false">
	         <include resource="logback-kafka.xml"/>
       ...

### 第五步:
######   集成完毕,启动项目
### 彩蛋:
######   日志收集服务logback-loggrab项目,将于近期开源,感谢关注该项目的所有开发者。
