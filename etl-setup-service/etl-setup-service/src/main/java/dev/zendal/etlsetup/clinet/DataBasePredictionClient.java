package dev.zendal.etlsetup.clinet;

import dev.zendal.etlsetup.domain.source.DataBasePrediction;
import dev.zendal.etlsetup.dto.databaseprediction.RecommendRequest;

import java.util.UUID;

public interface DataBasePredictionClient {

    DataBasePrediction predict(UUID id, RecommendRequest recommendRequest);
}
