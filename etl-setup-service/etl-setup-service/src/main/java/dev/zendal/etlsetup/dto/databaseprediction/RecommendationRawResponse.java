package dev.zendal.etlsetup.dto.databaseprediction;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class RecommendationRawResponse {

    @JsonProperty("recommended_databases")
    private List<RecommendedDatabase> recommendedDatabases;

    @JsonProperty("storage_strategy")
    private String storageStrategy;

    @JsonProperty("data_modeling_advice")
    private List<String> dataModelingAdvice;

    @JsonProperty("performance_optimizations")
    private List<String> performanceOptimizations;

    private boolean success;

    @Data
    public static class RecommendedDatabase {
        private String database;
        private String reason;
        private List<String> pros;
        private List<String> cons;

        @JsonProperty("suitability_score")
        private int suitabilityScore;
    }


}
