package dev.zendal.sourcebatch.service.spark;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.types.StructType;

public interface DataFrameStructureGenerator {

    StructType from(Dataset<Row> dataFrame);
}
