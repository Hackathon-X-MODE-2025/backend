package dev.zendal.etlsetup.service.source;

import dev.zendal.etlsetup.domain.source.EtlSessionSourceStatus;

import java.util.Optional;
import java.util.UUID;

public interface EltSessionSourceFindService {

    Optional<UUID> findOneByStatus(EtlSessionSourceStatus status);
}
