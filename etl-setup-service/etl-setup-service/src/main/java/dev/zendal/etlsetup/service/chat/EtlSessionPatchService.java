package dev.zendal.etlsetup.service.chat;

import dev.zendal.etlsetup.dto.request.EtlSessionChatPatchRequest;

import java.util.UUID;

public interface EtlSessionPatchService {

    void edit(UUID sessionId, EtlSessionChatPatchRequest dto);
}
