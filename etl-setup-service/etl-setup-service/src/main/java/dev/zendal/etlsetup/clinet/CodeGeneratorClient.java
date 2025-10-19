package dev.zendal.etlsetup.clinet;

import dev.zendal.etlsetup.dto.codegenerator.CodeGeneratorRequest;
import dev.zendal.etlsetup.dto.codegenerator.CodeGeneratorResponse;
import dev.zendal.etlsetup.dto.codegenerator.DagCodeGeneratorRequest;

import java.util.UUID;

public interface CodeGeneratorClient {

    CodeGeneratorResponse generateCode(UUID id, CodeGeneratorRequest codeGeneratorRequest);


    String regenerateDag(DagCodeGeneratorRequest codeGeneratorRequest);
}
