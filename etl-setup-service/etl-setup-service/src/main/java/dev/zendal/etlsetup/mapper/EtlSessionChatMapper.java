package dev.zendal.etlsetup.mapper;

import dev.zendal.etlsetup.configuration.MapperConfiguration;
import dev.zendal.etlsetup.domain.EtlSessionChatEntity;
import dev.zendal.etlsetup.dto.chat.EtlSessionChatDto;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfiguration.class)
public interface EtlSessionChatMapper {

    EtlSessionChatDto  toDto(EtlSessionChatEntity entity);
}
