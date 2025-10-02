package dev.zendal.etlsetup.service.session.picker;

import dev.zendal.etlsetup.domain.EtlSessionEntity;
import dev.zendal.etlsetup.domain.EtlSessionStatus;

import java.util.Optional;
import java.util.UUID;

public interface EtlSessionPickerService {

    EtlSessionEntity pickById(UUID id);

    Optional<EtlSessionEntity> pickByStatus(EtlSessionStatus status);

}
