package dev.zendal.etlsetup.service.chat;

import dev.zendal.etlsetup.domain.EtlSessionStatus;
import dev.zendal.etlsetup.dto.request.EtlSessionChatPatchRequest;
import dev.zendal.etlsetup.mapper.EtlSessionChatMapper;
import dev.zendal.etlsetup.service.codegenerator.CodeGeneratorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class EtlSessionPatchServiceImpl implements EtlSessionPatchService {

    private final EtlSessionChatFindService etlSessionChatFindService;

    private final CodeGeneratorService codeGeneratorService;

    private final EtlSessionChatMapper etlSessionChatMapper;

    @Override
    @Transactional
    public void edit(UUID sessionId, EtlSessionChatPatchRequest dto) {
        final var chat = this.etlSessionChatFindService.getLast(sessionId);

        log.info("Patching chat {} for session {}", chat.getId(), sessionId);

        if (chat.getEtlSession().getStatus() != EtlSessionStatus.USER_WAITING) {
            throw new IllegalArgumentException("Invalid status to patch");
        }

        if (dto.getDdl() != null) {
            chat.setDdl(dto.getDdl());
            chat.setDag(this.codeGeneratorService.regenerateDag(chat));
        }


        if (dto.getDag() != null) {
            chat.setDag(dto.getDag());
        }
    }
}
