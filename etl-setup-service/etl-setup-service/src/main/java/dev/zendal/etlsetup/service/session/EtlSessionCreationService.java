package dev.zendal.etlsetup.service.session;

import dev.zendal.etlsetup.dto.request.EtlSessionCreationRequest;
import dev.zendal.etlsetup.dto.response.EtlSessionDto;

public interface EtlSessionCreationService {

    EtlSessionDto create(EtlSessionCreationRequest request);
}
