package dev.zendal.etlsetup.controller.rest;

import dev.zendal.etlsetup.dto.request.SourceBatchCallbackRequest;
import dev.zendal.etlsetup.service.source.EtlSessionSourceProcessingTicker;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.zendal.backend.configuration.WebConstants;

import java.util.UUID;

@RestController
@RequestMapping(WebConstants.FULL_PRIVATE + "/batch-callback")
@RequiredArgsConstructor
public class SourceBatchRestController {

    private final EtlSessionSourceProcessingTicker etlSessionSourceProcessingTicker;

    @PostMapping("/{sourceId}")
    public void callback(@PathVariable UUID sourceId, @RequestBody SourceBatchCallbackRequest callbackRequest) {
        this.etlSessionSourceProcessingTicker.callback(sourceId, callbackRequest);
    }

}
