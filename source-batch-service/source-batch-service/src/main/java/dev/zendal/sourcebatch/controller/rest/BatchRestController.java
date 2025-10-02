package dev.zendal.sourcebatch.controller.rest;

import dev.zendal.sourcebatch.dto.source.SourceSettings;
import dev.zendal.sourcebatch.service.SourceBatchProcessingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.zendal.backend.configuration.WebConstants;

import java.util.UUID;

@RestController
@RequestMapping(WebConstants.FULL_PRIVATE + "/batch")
@RequiredArgsConstructor
public class BatchRestController {

    private final SourceBatchProcessingService sourceBatchProcessingService;

    @PostMapping("/{sourceId}")
    public void batch(@PathVariable UUID sourceId, @RequestBody SourceSettings sourceSettings) {
        this.sourceBatchProcessingService.process(sourceId, sourceSettings);
    }
}
