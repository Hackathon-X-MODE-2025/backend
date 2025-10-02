package dev.zendal.etlsetup.service.chat;

import dev.zendal.etlsetup.domain.EtlSessionChatEntity;
import dev.zendal.etlsetup.dto.chat.EtlSessionChatDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;

import java.util.UUID;

public interface EtlSessionChatFindService {

    PagedModel<EtlSessionChatDto> findBySessionId(UUID sessionId, Pageable pageable);

    EtlSessionChatEntity getLast(UUID sessionId);
}
