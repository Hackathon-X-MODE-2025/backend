package dev.zendal.etlsetup.dto.databaseprediction;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PerformanceRequirements {

    private long expectedSizeInGB;

    private String rateUpdateData;

}
