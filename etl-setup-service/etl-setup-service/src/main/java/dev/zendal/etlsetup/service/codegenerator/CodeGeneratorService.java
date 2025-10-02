package dev.zendal.etlsetup.service.codegenerator;

import dev.zendal.etlsetup.domain.EtlSessionChatEntity;
import dev.zendal.etlsetup.domain.EtlSessionEntity;
import dev.zendal.etlsetup.dto.codegenerator.CodeGeneratorResponse;

public interface CodeGeneratorService {

    CodeGeneratorResponse create(EtlSessionEntity etlSessionEntity);

    String regenerateDag(EtlSessionChatEntity chat);
}
