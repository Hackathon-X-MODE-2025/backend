package dev.zendal.sourcebatch.configuration;

import org.apache.spark.sql.SparkSession;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.Optional;

@Configuration
public class SparkConfiguration {

    @Bean
    public SparkSession sparkSession() {
        return SparkSession.builder()
                .appName(Optional.ofNullable(System.getenv("HOSTNAME")).orElse("localhost - " + LocalDateTime.now()))
                .master(Optional.ofNullable(System.getenv("SPARK_URL")).orElse("local[*]"))
                .config("spark.driver.host", Optional.ofNullable(System.getenv("POD_IP")).orElse("localhost"))
                //.config("spark.master", "spark://localhost:7077")
                //.config("spark.hadoop.fs.defaultFS", "hdfs://hadoop-hadoop-hdfs-nn:9000")

//                .config("spark.dynamicAllocation.enabled", "true")
//                .config("spark.dynamicAllocation.minExecutors", "0")
//                .config("spark.dynamicAllocation.maxExecutors", "100")
//                .config("spark.shuffle.service.enabled", "true")
                .getOrCreate();
    }
}
