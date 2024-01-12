package com.example.springbootkafka;

import com.example.springbootkafka.kafka.KafkaProducer;
import com.example.springbootkafka.modules.Log;
import org.springframework.context.ApplicationContext;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.*;

public class FileIngester {

    public static void sendToKafka(ApplicationContext context) throws InterruptedException {
        KafkaProducer kafkaProducer = context.getBean(KafkaProducer.class);

        String filePath = "/home/taha/Desktop/Logs";
        File directory = new File(filePath);
        WatchService watchService
                = null;
        try {
            watchService = FileSystems.getDefault().newWatchService();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Path path = directory.toPath();
        System.out.println(directory.toPath());
        try {
            path.register(
                    watchService,
                    StandardWatchEventKinds.ENTRY_CREATE,
                    StandardWatchEventKinds.ENTRY_DELETE,
                    StandardWatchEventKinds.ENTRY_MODIFY);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        WatchKey key;
        File[] files;
        while ((key = watchService.take()) != null) {
            files = directory.listFiles();
            if(files != null) {
                for (int i = 0; i < files.length; i++) {
                    getFiles(files[i], kafkaProducer);

                }
            }
            key.reset();
        }
    }

    private static void getFiles(File file, KafkaProducer kafkaProducer) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            String oneLog;
            while ((oneLog = bufferedReader.readLine()) != null) {
                String[] parts = oneLog.split("\s", 7);
                Log log = new Log();
                log.setDate(parts[0]);
                log.setTime(parts[1]);
                log.setComponentName(file.getName().split("-")[0]);
                log.setThreadName(parts[2]);
                log.setType(parts[3]);
                log.setClassName(parts[4]);
                log.setMessage(parts[6]);
                kafkaProducer.sendMessage(log);
            }

        } catch (IOException e) {
            System.out.println("IOException");
        } finally {
            file.delete();
        }
    }
}
