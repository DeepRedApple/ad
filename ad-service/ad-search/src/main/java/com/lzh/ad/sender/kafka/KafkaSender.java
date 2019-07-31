package com.lzh.ad.sender.kafka;

import com.alibaba.fastjson.JSON;
import com.lzh.ad.mysql.dto.MySqlRowData;
import com.lzh.ad.sender.ISender;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author Li
 **/
@Component("kafkaSender")
public class KafkaSender implements ISender {

	@Value("${adconf.kafka.topic}")
	private String topic;

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	@Override
	public void sender(MySqlRowData rowData) {
		kafkaTemplate.send(topic, JSON.toJSONString(rowData));
	}

	@KafkaListener(topics = {"ad-service-mysql-data"}, groupId = "ad-service")
	public void processMysqlRowData(ConsumerRecord<?, ?> record) {
		Optional<?> kafkaMessage = Optional.ofNullable(record.value());

		if (kafkaMessage.isPresent()) {
			Object message = kafkaMessage.get();
			MySqlRowData rowData =JSON.parseObject(message.toString(), MySqlRowData.class);
			System.out.println("kafka processMysqlRowData: " + JSON.toJSONString(rowData));
		}

	}
}
