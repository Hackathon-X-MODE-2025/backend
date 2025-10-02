package dev.zendal.hdfs.service;

import dev.zendal.hdfs.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.springframework.stereotype.Service;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class HDFSServiceImpl implements HDFSService {

    private final FileSystem hdfsFileSystem;

    @Override
    public void moveFromS3(String s3Key) {
        final var s3Url = Optional.ofNullable(System.getenv("S3_URL")).orElseThrow(
                () -> new EntityNotFoundException("S3_URL is not set")
        );

        s3Key = s3Key.startsWith("/") ? s3Key : "/" + s3Key;

        final var fileUrl = s3Url + s3Key;

        log.info("Loading file from {}", fileUrl);

        final File file;
        try {
            file = Files.createTempFile("asd", "saf").toFile();
        } catch (IOException e) {
            throw new EntityNotFoundException("Cat't create temp file");
        }

        try (BufferedInputStream in = new BufferedInputStream(new URL(fileUrl).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream(file)) {
            byte[] dataBuffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);
            }
            this.hdfsFileSystem.copyFromLocalFile(new Path(file.toURI()), new Path(s3Key));
        } catch (IOException e) {
            log.error("Error loading file", e);
            throw new EntityNotFoundException("Error loading file");
        } finally {
            file.delete();
        }
    }
}
