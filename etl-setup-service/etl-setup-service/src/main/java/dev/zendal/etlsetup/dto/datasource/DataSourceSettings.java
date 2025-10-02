package dev.zendal.etlsetup.dto.datasource;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import dev.zendal.etlsetup.domain.source.DataSourceType;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = HDFSDataSourceSettings.class),
        @JsonSubTypes.Type(value = PostgreSQLDataSourceSettings.class),
        @JsonSubTypes.Type(value = ClickHouseDataSourceSettings.class),
})
public interface DataSourceSettings {

    DataSourceType type();
}
