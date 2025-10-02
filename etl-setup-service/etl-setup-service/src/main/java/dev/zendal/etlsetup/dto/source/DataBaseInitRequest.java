package dev.zendal.etlsetup.dto.source;

import dev.zendal.etlsetup.dto.datasource.DataSourceSettings;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DataBaseInitRequest {

    private DataSourceSettings dataSourceSettings;

    private String ddl;
}
