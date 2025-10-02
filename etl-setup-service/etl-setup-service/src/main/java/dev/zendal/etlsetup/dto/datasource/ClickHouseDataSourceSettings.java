package dev.zendal.etlsetup.dto.datasource;

import dev.zendal.etlsetup.domain.source.DataSourceType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ClickHouseDataSourceSettings implements DataSourceSettings {

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

    @Override
    public DataSourceType type() {
        return DataSourceType.CLICK_HOUSE;
    }
}
