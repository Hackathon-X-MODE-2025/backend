package dev.zendal.etlsetup.service.source.status;

import dev.zendal.etlsetup.domain.source.EtlSessionSourceStatus;

import java.util.UUID;

public interface EtlSessionSourceStatusService {

    void change(UUID sessionSourceId, EtlSessionSourceStatus newStatus);
}
