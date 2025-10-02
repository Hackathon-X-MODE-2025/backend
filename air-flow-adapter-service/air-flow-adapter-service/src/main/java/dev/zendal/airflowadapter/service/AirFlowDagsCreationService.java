package dev.zendal.airflowadapter.service;

import dev.zendal.airflowadapter.dto.request.AirFlowDagCreationRequest;

public interface AirFlowDagsCreationService {

    void create(AirFlowDagCreationRequest request);
}
