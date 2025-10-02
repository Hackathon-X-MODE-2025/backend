package dev.zendal.etlsetup.dto.codegenerator;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class DagCodeGeneratorRequest extends CodeGeneratorRequest {

    @JsonProperty("ddl_results")
    private List<PipelineResponse.DDLResult> ddlResults;
}
