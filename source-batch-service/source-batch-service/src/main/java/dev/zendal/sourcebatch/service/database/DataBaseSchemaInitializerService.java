package dev.zendal.sourcebatch.service.database;

import dev.zendal.sourcebatch.dto.request.DataBaseInitRequest;

public interface DataBaseSchemaInitializerService {

    void initSchema(DataBaseInitRequest settings);
}
