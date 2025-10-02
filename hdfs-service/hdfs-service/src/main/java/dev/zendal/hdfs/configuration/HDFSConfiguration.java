package dev.zendal.hdfs.configuration;

import org.apache.hadoop.fs.FileSystem;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.Optional;

@Configuration
public class HDFSConfiguration {

    @Bean
    public FileSystem hdfsFileSystem() throws IOException {
        org.apache.hadoop.conf.Configuration conf = new org.apache.hadoop.conf.Configuration();
        conf.set("fs.defaultFS", Optional.ofNullable(System.getenv("HADOOP_URL")).orElse("hdfs://localhost"));

        return FileSystem.get(conf);
    }
}
