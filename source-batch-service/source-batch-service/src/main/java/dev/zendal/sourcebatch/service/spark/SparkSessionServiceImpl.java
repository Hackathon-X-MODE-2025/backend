package dev.zendal.sourcebatch.service.spark;

import org.apache.spark.sql.SparkSession;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class SparkSessionServiceImpl implements SparkSessionService {
    @Override
    public SparkSession getOrCreate(String description) {
        return SparkSession.builder()
                .appName(Optional.ofNullable(System.getenv("HOSTNAME")).orElse("localhost - " + LocalDateTime.now()) + ": " + description)
                .master("spark://spark-master-svc:7077")
                //.master("local[*]")
                .config("spark.driver.host", Optional.ofNullable(System.getenv("POD_IP")).orElse("localhost"))
                //.config("spark.master", "spark://localhost:7077")
                //.config("spark.hadoop.fs.defaultFS", "hdfs://hadoop-hadoop-hdfs-nn:9000")
                .config("spark.dynamicAllocation.enabled", "true")
                .config("spark.dynamicAllocation.minExecutors", "0")   // можно уйти в 0 при простое
                .config("spark.dynamicAllocation.initialExecutors", "1")
                .config("spark.dynamicAllocation.maxExecutors", "50")

                // Настройка idle timeout (в миллисекундах)
                .config("spark.dynamicAllocation.executorIdleTimeout", "60s")
                .config("spark.dynamicAllocation.cachedExecutorIdleTimeout", "300s")

                // Чтобы это работало — нужен external shuffle service
                .config("spark.shuffle.service.enabled", "true")
                .getOrCreate();
    }
}
