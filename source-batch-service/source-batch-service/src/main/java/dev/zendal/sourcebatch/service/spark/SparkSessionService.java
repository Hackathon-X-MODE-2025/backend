package dev.zendal.sourcebatch.service.spark;

import org.apache.spark.sql.SparkSession;

public interface SparkSessionService {

    SparkSession getOrCreate(String description);

    default SparkSession getOrCreate() {
        return this.getOrCreate("default");
    }
}
