package dev.zendal.etlsetup.dto.codegenerator;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CodeGeneratorJobResponse {

    @JsonProperty("job_id")
    private String jobId;

}
