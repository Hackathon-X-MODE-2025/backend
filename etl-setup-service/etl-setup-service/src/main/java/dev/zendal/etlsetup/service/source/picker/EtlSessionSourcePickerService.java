package dev.zendal.etlsetup.service.source.picker;

import dev.zendal.etlsetup.domain.source.EtlSessionSourceEntity;
import dev.zendal.etlsetup.domain.source.EtlSessionSourceStatus;

import java.util.Optional;
import java.util.UUID;

public interface EtlSessionSourcePickerService {

    EtlSessionSourceEntity pickById(UUID sourceId);

    Optional<EtlSessionSourceEntity> pickByStatus(EtlSessionSourceStatus status);
}
