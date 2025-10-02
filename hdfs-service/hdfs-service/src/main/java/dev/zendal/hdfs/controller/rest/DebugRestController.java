package dev.zendal.hdfs.controller.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zendal.backend.configuration.WebConstants;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;

@Slf4j
@RestController
@RequestMapping(WebConstants.FULL_WEB + "/debug")
@RequiredArgsConstructor
public class DebugRestController {


    @GetMapping
    public String t() throws IOException {

        final var fileUrl = "https://storage.yandexcloud.net/hack-2025/part1.json";
        final var file = Files.createTempFile("asd", "saf").toFile();

        try (BufferedInputStream in = new BufferedInputStream(new URL(fileUrl).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream(file)) {
            byte dataBuffer[] = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);
            }
        } catch (IOException e) {
            log.error("Error loading file", e);
            return "Err";
        }

        log.info("FILE LOADED");


        Configuration conf = new Configuration();
        conf.set("fs.defaultFS", "hdfs://hadoop-hadoop-hdfs-nn:9000");

        FileSystem fs = FileSystem.get(conf);


        fs.copyFromLocalFile(new Path(file.toURI()), new Path("/hadoop/test.txt"));
        return "OK";
    }
}
