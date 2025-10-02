package dev.zendal.etlsetup.dto.source;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import dev.zendal.etlsetup.dto.source.database.ClickHouseSourceSettings;
import dev.zendal.etlsetup.dto.source.database.PostgreSQLSourceSettings;
import dev.zendal.etlsetup.dto.source.hdfs.CsvHDFSSourceSettings;
import dev.zendal.etlsetup.dto.source.hdfs.JsonHDFSSourceSettings;
import dev.zendal.etlsetup.dto.source.hdfs.XmlHDFSSourceSettings;
import dev.zendal.etlsetup.dto.source.s3.CsvS3SourceSettings;
import dev.zendal.etlsetup.dto.source.s3.JsonS3SourceSettings;
import dev.zendal.etlsetup.dto.source.s3.XmlS3SourceSettings;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = CsvS3SourceSettings.class),
        @JsonSubTypes.Type(value = JsonS3SourceSettings.class),
        @JsonSubTypes.Type(value = XmlS3SourceSettings.class),
        @JsonSubTypes.Type(value = CsvHDFSSourceSettings.class),
        @JsonSubTypes.Type(value = JsonHDFSSourceSettings.class),
        @JsonSubTypes.Type(value = XmlHDFSSourceSettings.class),
        @JsonSubTypes.Type(value = PostgreSQLSourceSettings.class),
        @JsonSubTypes.Type(value = ClickHouseSourceSettings.class),
})
public interface SourceSettings {

    SourceSettingsType type();
}
