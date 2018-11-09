# logback-kafka
logback结合kafka实现日志推送,该项目抽离成module,方便以jar包的形式,进行依赖管理
即下即用,简单配置.使用方法详见如下:

# 下载后
### cd 当前项目(logback-kafka)根目录下 进行打包命令:
### mvn clean install

# 使用
### 第一步:
###### 需要日志收集的服务 pom.xml添加依赖:
   ```  
        <dependency>
            <groupId>com.learn</groupId>
            <artifactId>logback-kafka</artifactId>
            <version>0.0.1</version>
        </dependency>
   ```
#
### 第二步:
###### application.properties 添加kafka.servers和spring.application.name属性:
        spring.application.name=order    #(服务名将作为本服务的日志的topic)
        kafka.servers=127.0.0.1:9092     #(kafka地址:端口)
### 第三步:
######   通过maven管理查看源码打开logback-kafka目录下的src/mian/resources/文件夹下logback-kafka.xml配置文件
######   将其拷贝至需要日志收集自身服务的src/mian/resources/目录下

### 第四步:
######   logback.xml或logback-spring.xml,添加:
       <include resource="logback-kafka.xml"/>
###### 引入copy的logback-kafka.xml文件进行加载.

# logback-kafka.xml中,名为KAFKA的appender组件配置介绍:

### topic: 默认加载application.properties配置文件里的spring.application.name的值
`<topic>${springAppName:-}</topic>`

### servers: 默认加载application.properties配置文件里的kafka.servers配置的值
`<servers>${servers:-}</servers>`

### kafka生产者相关配置:
```<acks>1</acks>
   <retries>0</retries>
   <batchSize>4096</batchSize>
   <linger>1</linger>
  <bufferMemory>409600</bufferMemory>
```

### kafka推送日志记录是否开启(记录到本地) 生产环境建议关闭修改为:false
`<enabledLog>true</enabledLog>`

### kafka推送日志记录组件logger名
`<kafkaLogger>kafkaSendLog</kafkaLogger>`

## kafka推送日志记录logger组件默认配置:
```
    <appender name="KAFKA-LOG-ERROR"  class="ch.qos.logback.core.rolling.RollingFileAppender">
        <filter class="ch.qos.logback.classic.filter.ThresholdFilter">
            <level>ERROR</level>
        </filter>
        <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
            <!--日志文件输出的文件名-->
            <FileNamePattern>${KAFKA_LOG_HOME}/error.%d{yyyy-MM-dd}.log</FileNamePattern>
            <!--日志文件保留天数-->
            <MaxHistory>30</MaxHistory>
        </rollingPolicy>
        <encoder class="ch.qos.logback.classic.encoder.PatternLayoutEncoder">
            <!--格式化输出：%d表示日期，%thread表示线程名，%-5level：级别从左显示5个字符宽度%msg：日志消息，%n是换行符-->
            <pattern>%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] %-5level [%L] : %msg%n</pattern>
        </encoder>
    </appender>
    
    <appender name="KAFKA-LOG-INFO"  class="ch.qos.logback.core.rolling.RollingFileAppender">
        <filter class="ch.qos.logback.classic.filter.ThresholdFilter">
            <level>INFO</level>
        </filter>
        <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
            <!--日志文件输出的文件名-->
            <FileNamePattern>${KAFKA_LOG_HOME}/info.%d{yyyy-MM-dd}.log</FileNamePattern>
            <!--日志文件保留天数-->
            <MaxHistory>30</MaxHistory>
        </rollingPolicy>
        <encoder class="ch.qos.logback.classic.encoder.PatternLayoutEncoder">
            <!--格式化输出：%d表示日期，%thread表示线程名，%-5level：级别从左显示5个字符宽度%msg：日志消息，%n是换行符-->
            <pattern>%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] %-5level [%L] : %msg%n</pattern>
        </encoder>
    </appender>
    
    <logger name="kafkaSendLog" level="INFO" additivity="false"> 
        <appender-ref ref="KAFKA-LOG-INFO" /> 
        <appender-ref ref="KAFKA-LOG-ERROR" /> 
    </logger>
```

# 如有其它,或引入该依赖jar包遇到问题,请联系:345684180@qq.com,谢谢
