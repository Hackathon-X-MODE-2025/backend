package dev.zendal.sourcebatch.controller.rest;

import dev.zendal.sourcebatch.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.zendal.backend.configuration.WebConstants;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping(WebConstants.FULL_WEB + "/debug")
@RequiredArgsConstructor
public class DebugRestController {

    private final FileSystem hdfsFileSystem;

    @GetMapping("/s3-to-hdfs")
    public String migrateToHadoop(@RequestParam String fileName) throws IOException {
        final var s3Url = Optional.ofNullable(System.getenv("S3_URL")).orElseThrow(
                () -> new EntityNotFoundException("S3_URL is not set")
        );

        final var fileUrl = s3Url + "/" + fileName;


        final var file = Files.createTempFile("asd", "saf").toFile();

        try (BufferedInputStream in = new BufferedInputStream(new URL(fileUrl).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream(file)) {
            byte dataBuffer[] = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);
            }
            this.hdfsFileSystem.copyFromLocalFile(new Path(file.toURI()), new Path("/" + fileName));
        } catch (IOException e) {
            log.error("Error loading file", e);
            return "Err";
        } finally {
            file.delete();
        }


        return "Success";
    }

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
