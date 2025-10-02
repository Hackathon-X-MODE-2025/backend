package dev.zendal.etlsetup.clinet;


import java.util.UUID;

public interface AirFlowAdapterClient {

    void saveDag(UUID requestId, String dagCode);
}
