package dev.zendal.etlsetup.controller.rest;

import dev.zendal.etlsetup.dto.airflow.AirFlowCallbackRequest;
import dev.zendal.etlsetup.service.session.airflow.EtlSessionAirFlowService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.zendal.backend.configuration.WebConstants;

import java.util.UUID;

@RestController
@RequestMapping(WebConstants.FULL_PRIVATE + "/airflow-callback")
@RequiredArgsConstructor
public class AirFlowRestController {

    private final EtlSessionAirFlowService etlSessionAirFlowService;

    @PostMapping("/{sessionId}")
    public void callback(@PathVariable UUID sessionId, @RequestBody AirFlowCallbackRequest callbackRequest) {
        this.etlSessionAirFlowService.acceptCallback(sessionId, callbackRequest.isSuccess(), callbackRequest.getAirflowId());
    }

}
