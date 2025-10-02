package dev.zendal.etlsetup.clinet;

import dev.zendal.etlsetup.dto.codegenerator.CodeGeneratorRequest;
import dev.zendal.etlsetup.dto.codegenerator.CodeGeneratorResponse;
import dev.zendal.etlsetup.dto.codegenerator.DagCodeGeneratorRequest;

public interface CodeGeneratorClient {

    CodeGeneratorResponse generateCode(CodeGeneratorRequest codeGeneratorRequest);


    String regenerateDag(DagCodeGeneratorRequest codeGeneratorRequest);
}
