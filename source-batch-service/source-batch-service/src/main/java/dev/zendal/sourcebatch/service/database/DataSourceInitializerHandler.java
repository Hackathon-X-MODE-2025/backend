package dev.zendal.sourcebatch.service.database;

import dev.zendal.sourcebatch.dto.datasource.DataSourceType;
import dev.zendal.sourcebatch.dto.request.DataBaseInitRequest;

public interface DataSourceInitializerHandler {

    void initSchema(DataBaseInitRequest settings);

    DataSourceType supportedDataSourceSettings();
}
