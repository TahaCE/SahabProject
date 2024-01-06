package com.example.springbootkafka;

import com.example.springbootkafka.kafka.KafkaProducer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
@SpringBootApplication
public class FileIngester {

	public static void main(String[] args) {
		SpringApplication.run(FileIngester.class, args);
		KafkaProducer kafkaProducer = context.getBean(KafkaProducer.class);

		String filePath = "D:\\sahbino\\Logs";
		File directory = new File(filePath);
		File[] files = directory.listFiles();
		assert files != null;
	}

}
