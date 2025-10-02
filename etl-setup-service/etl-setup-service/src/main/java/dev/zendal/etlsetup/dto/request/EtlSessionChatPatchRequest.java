package dev.zendal.etlsetup.dto.request;

import dev.zendal.etlsetup.dto.codegenerator.PipelineResponse;
import lombok.Data;

import java.util.List;

@Data
public class EtlSessionChatPatchRequest {

    private List<PipelineResponse.DDLResult> ddl;

    private String dag;

}
