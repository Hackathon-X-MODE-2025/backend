package dev.zendal.sourcebatch.dto.request;

import dev.zendal.sourcebatch.dto.datasource.DataSourceSettings;
import lombok.Data;

@Data
public class DataBaseInitRequest {

    private DataSourceSettings dataSourceSettings;

    private String ddl;
}
