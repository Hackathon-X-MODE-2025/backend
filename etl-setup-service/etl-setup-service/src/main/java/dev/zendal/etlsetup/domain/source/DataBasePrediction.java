package dev.zendal.etlsetup.domain.source;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DataBasePrediction {

    private List<DataBaseRecommendation> recommendations;


    @Data
    @Builder
    public static class DataBaseRecommendation {
        private DataSourceType dataSource;

        private String reason;

        private List<String> pros;

        private List<String> cons;

        private int suitabilityScore;
    }

    private List<String> performanceOptimizations;

}
