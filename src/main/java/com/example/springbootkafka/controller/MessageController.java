package com.example.springbootkafka.controller;

import com.example.springbootkafka.kafka.KafkaProducer;
import com.example.springbootkafka.modules.Log;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/kafka")
public class MessageController {
    private KafkaProducer kafkaProducer;

    public MessageController(KafkaProducer kafkaProducer) {
        this.kafkaProducer = kafkaProducer;
    }
    //http:localhost:8080/api/v1/kafka/publish
    @GetMapping("/publish")
    public ResponseEntity<String> publish(@RequestBody Log log){
        kafkaProducer.sendMessage(log);
        return ResponseEntity.ok("Json message sent to the topic");
    }
}
