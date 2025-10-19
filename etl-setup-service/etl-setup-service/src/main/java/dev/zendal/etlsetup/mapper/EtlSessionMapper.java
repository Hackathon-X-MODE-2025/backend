package dev.zendal.etlsetup.mapper;

import dev.zendal.etlsetup.configuration.MapperConfiguration;
import dev.zendal.etlsetup.domain.EtlSessionEntity;
import dev.zendal.etlsetup.dto.request.EtlSessionCreationRequest;
import dev.zendal.etlsetup.dto.response.EtlSessionDto;
import dev.zendal.etlsetup.dto.response.EtlSessionShortDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfiguration.class, uses = EtlSessionSourceMapper.class)
public interface EtlSessionMapper {

    EtlSessionDto from(EtlSessionEntity entity);

    EtlSessionShortDto toShort(EtlSessionEntity etlSessionEntity);


    @Mapping(target = "process", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "airflowId", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "sources", ignore = true)
    @Mapping(target = "modifiedDate", ignore = true)
    @Mapping(target = "expectedSizeInGb", source = "expectedSizeInGB")
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "dataBasePrediction", ignore = true)
    @Mapping(target = "dataSourceSettings", ignore = true)
    @Mapping(target = "chats", ignore = true)
    EtlSessionEntity to(EtlSessionCreationRequest etlSessionCreationRequest);
}
