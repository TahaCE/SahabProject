package com.example.springbootkafka;
import java.io.*;
import java.nio.file.*;
public class test {
    public static void main(String[] args) throws IOException, InterruptedException {
        String filepath = "D:\\5\\Algo\\work\\hw3\\src\\logs";
        File directory = new File(filepath);
        WatchService watchService
                = FileSystems.getDefault().newWatchService();

        Path path = directory.toPath();
        System.out.println(directory.toPath());
        path.register(
                watchService,
                StandardWatchEventKinds.ENTRY_CREATE,
                StandardWatchEventKinds.ENTRY_DELETE,
                StandardWatchEventKinds.ENTRY_MODIFY);

        WatchKey key;
        File[] files;
        while ((key = watchService.take()) != null) {
            files = directory.listFiles();
            if(files != null) {
                for (int i = 0; i < files.length; i++) {
                    try (BufferedReader bufferedReader = new BufferedReader(new FileReader(files[i]))) {
                        String stringLog;
                        while ((stringLog = bufferedReader.readLine()) != null) {
                            System.out.println(stringLog);
                        }

                    } catch (IOException e) {
                        System.out.println("IOException");
                    } finally {
                        files[i].delete();
                    }
                }
            }
            key.reset();
        }

    }
}
