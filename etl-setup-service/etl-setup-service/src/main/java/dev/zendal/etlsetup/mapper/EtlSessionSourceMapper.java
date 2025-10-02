package dev.zendal.etlsetup.mapper;

import dev.zendal.etlsetup.configuration.MapperConfiguration;
import dev.zendal.etlsetup.domain.source.EtlSessionSourceEntity;
import dev.zendal.etlsetup.dto.response.EtlSessionSourceDto;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfiguration.class)
public interface EtlSessionSourceMapper {

    EtlSessionSourceDto from(EtlSessionSourceEntity entity);
}
