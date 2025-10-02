package dev.zendal.etlsetup.service.chat;

import java.util.UUID;

public interface EtlSessionChatCommitService {

    void commit(UUID sessionId);
}
