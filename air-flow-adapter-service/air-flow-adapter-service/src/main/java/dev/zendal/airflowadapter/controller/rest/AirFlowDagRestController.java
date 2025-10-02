package dev.zendal.airflowadapter.controller.rest;

import dev.zendal.airflowadapter.dto.request.AirFlowDagCreationRequest;
import dev.zendal.airflowadapter.service.AirFlowDagsCreationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import org.zendal.backend.configuration.WebConstants;

@RestController
@RequestMapping(WebConstants.FULL_PRIVATE + "/dags")
@RequiredArgsConstructor
public class AirFlowDagRestController {

    private final AirFlowDagsCreationService airFlowDagsCreationService;


    @PostMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void register(@Valid @RequestBody AirFlowDagCreationRequest request) {
        this.airFlowDagsCreationService.create(request);
    }
    
}
