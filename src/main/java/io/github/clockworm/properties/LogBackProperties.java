package io.github.clockworm.properties;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "io.github.clockworm.middle.logback")
@Component
@Data
public class LogBackProperties {
	/**kafka地址 多个逗号隔开*/
	private String kafkaServers;
	/**项目组名称*/
	private String projectGroupName;
	/**项目名称*/
	private String projectAppName;
	/**推送日志格式*/
	private String logPattern;
}