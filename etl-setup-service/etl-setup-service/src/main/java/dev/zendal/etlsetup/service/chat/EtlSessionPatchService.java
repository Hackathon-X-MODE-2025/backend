package dev.zendal.etlsetup.service.chat;

import dev.zendal.etlsetup.dto.chat.EtlSessionChatDto;
import dev.zendal.etlsetup.dto.request.EtlSessionChatPatchRequest;

import java.util.UUID;

public interface EtlSessionPatchService {

    EtlSessionChatDto edit(UUID sessionId, EtlSessionChatPatchRequest dto);
}
