package dev.zendal.etlsetup.clinet;


import dev.zendal.etlsetup.dto.source.DataBaseInitRequest;
import dev.zendal.etlsetup.dto.source.SourceSettings;

import java.util.UUID;

public interface SourceBatchClient {

    void sendProcessing(UUID sourceId, SourceSettings settings);

    void initDataBase(DataBaseInitRequest request);
}
