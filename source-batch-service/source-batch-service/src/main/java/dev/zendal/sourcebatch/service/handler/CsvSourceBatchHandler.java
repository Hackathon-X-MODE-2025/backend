package dev.zendal.sourcebatch.service.handler;

import dev.zendal.sourcebatch.dto.source.SourceSettings;
import dev.zendal.sourcebatch.dto.source.SourceSettingsType;
import dev.zendal.sourcebatch.dto.source.raw.CsvSourceSettings;
import dev.zendal.sourcebatch.service.spark.DataFrameStructureGenerator;
import lombok.RequiredArgsConstructor;
import org.apache.spark.sql.SparkSession;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CsvSourceBatchHandler extends AbstractFileSourceBatchHandler {

    private final SparkSession sparkSession;
    private final DataFrameStructureGenerator dataFrameStructureGenerator;

    @Override
    protected String process(String[] fileLocations, SourceSettings sourceSettings) {

        final var csv = (CsvSourceSettings) sourceSettings;

        final var dataFrame = sparkSession.read()
                .option("mode", "PERMISSIVE")
                .option("delimiter", csv.getDelimiter())
                .option("multiline", "true")
                .csv(fileLocations);

        return this.dataFrameStructureGenerator.from(dataFrame).json();
    }

    @Override
    public SourceSettingsType type() {
        return SourceSettingsType.CSV;
    }
}
