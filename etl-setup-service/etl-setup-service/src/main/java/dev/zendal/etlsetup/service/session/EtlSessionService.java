package dev.zendal.etlsetup.service.session;

import dev.zendal.etlsetup.domain.EtlSessionEntity;
import dev.zendal.etlsetup.dto.response.EtlSessionDto;

public interface EtlSessionService {

    EtlSessionDto create(EtlSessionEntity entity);
}
