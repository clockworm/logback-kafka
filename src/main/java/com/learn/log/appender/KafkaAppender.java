package com.learn.log.appender;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;
import ch.qos.logback.core.Layout;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class KafkaAppender extends AppenderBase<ILoggingEvent> {
	private String topic;
	private String servers;
	private String acks;
	private int retries;
	private int batchSize;
	private int linger;
	private int bufferMemory;
	/**是否开启KAFKA发送日志 注意当true时需要结合LOGBACK配置名为kafkaLogger的<logger>...</logger>标签*/
	private boolean enabledLog = false;
	/**logger标签的名称*/
	private String kafkaLogger;
	/**日志输出组件*/
	private Layout<ILoggingEvent> layout;

	private KafkaTemplate<String, String> logProducer;

	private ExecutorService kafkaPool = Executors.newSingleThreadExecutor();

	private static final String  ERROR_SERVERS = "servers_IS_UNDEFINED";

	private Logger kafkaLog  = null;

	@Override
	public void start() {
		if(enabledLog) {
			kafkaLog = LoggerFactory.getLogger(kafkaLogger);
		}
		if(!ERROR_SERVERS.equals(servers) && logProducer==null) {
			Map<String, Object> props = new HashMap<>();
			props.put(ProducerConfig.ACKS_CONFIG, acks);
			props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, servers);
			props.put(ProducerConfig.RETRIES_CONFIG, retries);
			props.put(ProducerConfig.BATCH_SIZE_CONFIG, batchSize);
			props.put(ProducerConfig.LINGER_MS_CONFIG, linger);
			props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, bufferMemory);
			props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
			props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
			DefaultKafkaProducerFactory<String, String> producerFactory = new DefaultKafkaProducerFactory<>(props,new StringSerializer(), new StringSerializer());
			this.logProducer = new KafkaTemplate<>(producerFactory);
		}
		super.start();
	}

	@Override
	public void stop() {
		super.stop();
	}

	@Override
	protected void append(ILoggingEvent event) {
		String level = event.getLevel().levelStr;
		String doLayout = layout.doLayout(event);
		try {
			this.send(topic.toLowerCase(), level, doLayout);
		} catch (Exception e) {
			if(enabledLog) {
				kafkaLog.error("Kafka日志组件,发送日志数据异常:{}",e);
			}
		}

	}

	/** 日志数据发送方法 */
	private void send(String topic, String key, String message){
		if(logProducer==null) return;
		kafkaPool.execute(new Runnable(){
			@Override
			public void run() {
				ListenableFuture<SendResult<String, String>> future = logProducer.send(topic, key, message);
				if(enabledLog) {
					future.addCallback(o -> kafkaLog.info("send-日志数据发送成功 topic:{},partition:{},offset:{}",o.getRecordMetadata().topic(),o.getRecordMetadata().partition(),o.getRecordMetadata().offset()),
							throwable -> kafkaLog.error("send-日志数据发送失败 topic:{},log:{}", topic, message));
				}
			}
		});
	}

}
