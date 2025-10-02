package dev.zendal.etlsetup.dto.codegenerator;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CodeGeneratorResponse {

    private List<PipelineResponse.DDLResult> ddlResults;

    private String dagCode;

    private Map<Object, Object> pipelineVisualization;

}
