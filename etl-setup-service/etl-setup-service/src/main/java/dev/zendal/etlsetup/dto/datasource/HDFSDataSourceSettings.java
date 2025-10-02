package dev.zendal.etlsetup.dto.datasource;

import dev.zendal.etlsetup.domain.source.DataSourceType;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class HDFSDataSourceSettings implements DataSourceSettings {

    @NotNull
    private String path;

    @Override
    public DataSourceType type() {
        return DataSourceType.HDFS;
    }
}
