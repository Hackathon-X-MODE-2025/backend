package dev.zendal.etlsetup.service.session.airflow;

import java.util.UUID;

public interface EtlSessionAirFlowService {

    void acceptCallback(UUID sessionId, boolean success, UUID airflowId);
}
