package dev.zendal.sourcebatch.service.handler;

import dev.zendal.sourcebatch.dto.source.SourceSettings;
import dev.zendal.sourcebatch.dto.source.SourceSettingsType;
import dev.zendal.sourcebatch.dto.source.hdfs.HDFSSourceSettings;
import dev.zendal.sourcebatch.dto.source.raw.XmlSourceSettings;
import dev.zendal.sourcebatch.service.spark.DataFrameStructureGenerator;
import lombok.RequiredArgsConstructor;
import org.apache.spark.sql.SparkSession;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public class XmlSourceBatchHandler extends AbstractFileSourceBatchHandler {
    private final SparkSession sparkSession;

    private final DataFrameStructureGenerator dataFrameStructureGenerator;

    @Override
    protected String process(String[] fileLocations, SourceSettings sourceSettings) {

        final var xml = (XmlSourceSettings) sourceSettings;

        final var dataFrame = sparkSession.read()
                .option("mode", "PERMISSIVE")
                .option("rowTag", xml.getRootTag())
                .option("multiline", "true")
                .xml(fileLocations);

        return this.dataFrameStructureGenerator.from(dataFrame).json();
    }

    @Override
    public SourceSettingsType type() {
        return SourceSettingsType.XML;
    }
}
