package dev.zendal.airflowadapter.client;

import java.util.UUID;

public interface EtlSetupClient {

    void notify(UUID fallbackId, boolean success, UUID airflowId);
}
