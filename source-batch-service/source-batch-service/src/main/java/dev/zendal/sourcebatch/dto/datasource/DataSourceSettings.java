package dev.zendal.sourcebatch.dto.datasource;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = PostgreSQLDataSourceSettings.class),
        @JsonSubTypes.Type(value = ClickHouseDataSourceSettings.class),
})
public interface DataSourceSettings {

    DataSourceType type();
}
