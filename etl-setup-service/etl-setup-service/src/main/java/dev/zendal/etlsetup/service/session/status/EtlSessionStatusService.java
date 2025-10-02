package dev.zendal.etlsetup.service.session.status;

import dev.zendal.etlsetup.domain.EtlSessionStatus;

import java.util.UUID;

public interface EtlSessionStatusService {

    void change(UUID sessionId, EtlSessionStatus newStatus);
}
