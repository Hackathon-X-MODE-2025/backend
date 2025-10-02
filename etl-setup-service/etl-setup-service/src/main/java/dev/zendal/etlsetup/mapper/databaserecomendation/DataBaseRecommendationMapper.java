package dev.zendal.etlsetup.mapper.databaserecomendation;

import dev.zendal.etlsetup.configuration.MapperConfiguration;
import dev.zendal.etlsetup.domain.source.DataBasePrediction;
import dev.zendal.etlsetup.domain.source.DataSourceType;
import dev.zendal.etlsetup.dto.databaseprediction.RecommendationRawResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfiguration.class)
public interface DataBaseRecommendationMapper {


    @Mapping(target = "dataSource", source = "database")
    DataBasePrediction.DataBaseRecommendation from(RecommendationRawResponse.RecommendedDatabase recommendedDatabase);


    default DataSourceType from(String database) {
        final var lower = database.toLowerCase();
        if (lower.startsWith("timescale")) {
            return DataSourceType.TIMESCALE_DB;
        }
        if (lower.startsWith("postgres")) {
            return DataSourceType.POSTGRES;
        }

        if (lower.startsWith("clickhouse")) {
            return DataSourceType.CLICK_HOUSE;
        }

        if (lower.startsWith("mongo")) {
            return DataSourceType.MONGO_DB;
        }
        if (lower.startsWith("cassandra")) {
            return DataSourceType.CASSANDRA;
        }

        if (lower.startsWith("elastic")) {
            return DataSourceType.ELASTICSEARCH;
        }

        if (lower.startsWith("scylla")) {
            return DataSourceType.SCYLLA_DB;
        }

        if (lower.startsWith("hdfs")) {
            return DataSourceType.HDFS;
        }
        return null;
    }
}
