package com.example.springbootkafka;

import com.example.springbootkafka.kafka.KafkaProducer;

import com.example.springbootkafka.modules.Log;
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
		ApplicationContext context = SpringApplication.run(FileIngester.class, args);
		KafkaProducer kafkaProducer = context.getBean(KafkaProducer.class);

		String filePath = "/home/taha/Desktop/Logs";
		File directory = new File(filePath);
		File[] files = directory.listFiles();
		assert files != null;
		for (File file : files) {
			try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
				String stringLog;
				String topic = file.getName().split("-")[0];
				while ((stringLog = bufferedReader.readLine()) != null) {
					String[] parts = stringLog.split(" ", 7);
					Log log = new Log();
					log.setDate(parts[0]);
					log.setTime(parts[1]);
					log.setComponentName(topic);
					log.setThreadName(parts[2]);
					log.setType(parts[3]);
					log.setClassName(parts[4]);
					log.setMessage(parts[6]);
					KafkaProducer.sendMessage(log);
				}
				try
				{
					while(!file.delete());
				}
				catch (Exception e) {
					throw new RuntimeException(e);
				}
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}

}
