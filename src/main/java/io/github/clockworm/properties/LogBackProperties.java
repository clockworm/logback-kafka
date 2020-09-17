package io.github.clockworm.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@ConfigurationProperties(prefix = "io.github.clockworm.middle.logback")
@Component
@Data
public class LogBackProperties {
	private String kafkaServers;
	private String projectAppName;
	private String projectGroupName;
}