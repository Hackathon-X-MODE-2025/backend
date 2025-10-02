package dev.zendal.etlsetup.dto.codegenerator;

import com.fasterxml.jackson.annotation.JsonProperty;
import dev.zendal.etlsetup.dto.datasource.DataSourceSettings;
import dev.zendal.etlsetup.dto.source.SourceSettings;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@Builder
public class CodeGeneratorSchema {

    private List<CodeGeneratorSchemaSettings> inputs;

    private DataSourceSettings output;

    @JsonProperty("processing_requirements")
    private Map<String, Object> processingRequirements;

    @Data
    @Builder
    public static class CodeGeneratorSchemaSettings {

        private SourceSettings settings;

        private Object schema;
    }
}
