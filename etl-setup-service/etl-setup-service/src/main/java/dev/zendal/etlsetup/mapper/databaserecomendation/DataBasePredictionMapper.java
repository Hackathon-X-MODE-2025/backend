package dev.zendal.etlsetup.mapper.databaserecomendation;

import dev.zendal.etlsetup.configuration.MapperConfiguration;
import dev.zendal.etlsetup.domain.source.DataBasePrediction;
import dev.zendal.etlsetup.dto.databaseprediction.RecommendationRawResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfiguration.class, uses = DataBaseRecommendationMapper.class)
public interface DataBasePredictionMapper {

    @Mapping(target = "recommendations", source = "recommendedDatabases")
    DataBasePrediction from(RecommendationRawResponse recommendationRawResponse);
}
