package dev.zendal.sourcebatch.service.handler;

import dev.zendal.sourcebatch.dto.source.SourceSettings;
import dev.zendal.sourcebatch.dto.source.SourceSettingsType;
import dev.zendal.sourcebatch.service.spark.DataFrameStructureGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.spark.sql.SparkSession;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class JsonSourceBatchHandler extends AbstractFileSourceBatchHandler {

    private final SparkSession sparkSession;

    private final DataFrameStructureGenerator dataFrameStructureGenerator;

    @Override
    protected String process(String[] fileLocations, SourceSettings sourceSettings) {
        final var dataFrame = sparkSession.read()
                .option("mode", "PERMISSIVE")
                .option("multiline", "true")
                .json(fileLocations);

        return this.dataFrameStructureGenerator.from(dataFrame).json();
    }

    @Override
    public SourceSettingsType type() {
        return SourceSettingsType.JSON;
    }
}
