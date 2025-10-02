package dev.zendal.etlsetup.dto.codegenerator;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Builder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class CodeGeneratorRequest {

    @JsonProperty("schema_input")
    private CodeGeneratorSchema schemaInput;

    @JsonProperty("target_db")
    private String targetDb;

    @JsonProperty("source_type")
    private String sourceType;

    @JsonProperty("dag_template")
    private String dagTemplate;
}
