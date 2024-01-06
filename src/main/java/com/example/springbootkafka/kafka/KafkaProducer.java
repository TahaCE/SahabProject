package com.example.springbootkafka.kafka;

import com.example.springbootkafka.modules.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducer {
    private static final Logger logger = LoggerFactory.getLogger(KafkaProducer.class);
    private static KafkaTemplate<String, Log> kafkaTemplate;

    @Autowired
    public KafkaProducer(KafkaTemplate<String, Log> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public static void sendMessage(Log log){
        logger.info(String.format("Message sent -> %s", log.toString()));
        Message<Log> message = MessageBuilder.withPayload(log).setHeader(KafkaHeaders.TOPIC, "myTopic").build();
        kafkaTemplate.send(message);
    }
}
