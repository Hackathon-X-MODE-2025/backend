package dev.zendal.hdfs.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;

import java.net.URI;

@Configuration
public class S3Configuration {

    @Bean
    public S3Client s3Client() {
        final var cred = AwsBasicCredentials.builder()
                .accessKeyId("YCAJEgMj4xjpmdDZSpzoBu943")
                .secretAccessKey("YCMC4OoiSgS7rZF1KR3nrwqE6TEGsigQaOAFNweA")
                .build();
        return S3Client.builder()
                .region(Region.of("ru-central-1"))
                .credentialsProvider(
                        StaticCredentialsProvider.create(cred)
                )
                .endpointOverride(URI.create("https://storage.yandexcloud.net"))
                .serviceConfiguration(software.amazon.awssdk.services.s3.S3Configuration.builder()
                        .pathStyleAccessEnabled(true) // Для MinIO или других совместимых сервисов
                        .build()
                )
                .build();
    }

    @Bean
    public S3Presigner s3Presigner() {
        final var cred = AwsBasicCredentials.builder()
                .accessKeyId("YCAJEgMj4xjpmdDZSpzoBu943")
                .secretAccessKey("YCMC4OoiSgS7rZF1KR3nrwqE6TEGsigQaOAFNweA")
                .build();
        return S3Presigner.builder()
                .region(Region.of("ru-central-1"))
                .credentialsProvider(
                        StaticCredentialsProvider.create(cred)
                )
                .endpointOverride(URI.create("https://storage.yandexcloud.net"))
                .serviceConfiguration(software.amazon.awssdk.services.s3.S3Configuration.builder()
                        .pathStyleAccessEnabled(true) // Для MinIO или других совместимых сервисов
                        .build()
                )
                .build();
    }
}
