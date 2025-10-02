package dev.zendal.etlsetup.service.session;

import dev.zendal.etlsetup.dto.datasource.DataSourceSettings;

import java.util.UUID;

public interface EtlSessionUserSelectDataSourceService {

    void selectDataSource(UUID sessionId, DataSourceSettings dataSourceSettings);
}
