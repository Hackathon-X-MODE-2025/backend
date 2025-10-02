package dev.zendal.sourcebatch.service;

import dev.zendal.sourcebatch.dto.source.SourceSettings;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.UUID;

public interface SourceBatchProcessingService {

    void process(UUID sourceId, @RequestBody SourceSettings sourceSettings);
}
