package com.example.springbootkafka;



import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;



@SpringBootApplication
public class SpringAplication {

	public static void main(String[] args) throws InterruptedException {
		ApplicationContext context = SpringApplication.run(SpringAplication.class, args);
		FileIngester.sendToKafka(context);
	}

}
