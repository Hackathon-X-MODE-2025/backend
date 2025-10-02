package dev.zendal.etlsetup.dto.databaseprediction;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RecommendRequest {

    @JsonProperty("use_case")
    private String useCase;

    @JsonProperty("data_characteristics")
    private Object dataCharacteristics;

    @JsonProperty("performance_requirements")
    private PerformanceRequirements performanceRequirements;

}
