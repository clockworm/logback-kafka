# logback-kafka
logback结合kafka实现日志推送,该项目抽离成module,方便以jar包的形式,进行依赖管理
即下即用,简单配置.

# 使用方法详见如下:
### 第一步:
###### 需要日志收集的服务 pom.xml添加依赖:
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
        io.github.clockworm.middle.logback.project-group-name=项目组名           #(注意不允许带下划线或其他特殊符号 项目组名 比如:cbest-bee)
        io.github.clockworm.middle.logback.project-app-name=服务名               #(注意不允许带下划线或其他特殊符号  服务名  比如:gateway)
        io.github.clockworm.middle.logback.kafka-servers=xxx.xxx.xxx.xxx:9092    #(kafka地址:端口)
### 第三步:
######   通过maven管理查看源码打开logback-kafka包的logback-kafka.xml配置文件
######   将其拷贝至需要自身服务的src/mian/resources/目录下

### 第四步:
######   logback.xml或logback-spring.xml,添加:
       <include resource="logback-kafka.xml"/>
###### 引入copy的logback-kafka.xml文件进行加载.

### 第五步:
######   集成完毕,启动项目
