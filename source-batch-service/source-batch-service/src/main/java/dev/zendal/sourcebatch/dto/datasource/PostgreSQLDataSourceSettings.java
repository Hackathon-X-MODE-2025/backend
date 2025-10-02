package dev.zendal.sourcebatch.dto.datasource;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PostgreSQLDataSourceSettings implements DataSourceSettings {

    @NotNull
    private String host;

    @Min(1)
    @NotNull
    private Integer port;

    @NotNull
    private String username;

    @NotNull
    private String password;

    @NotNull
    private String database;

    @NotNull
    private String schema;

    @Override
    public DataSourceType type() {
        return DataSourceType.POSTGRES;
    }
}
