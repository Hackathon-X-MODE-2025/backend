package dev.zendal.etlsetup.service.chat;

import dev.zendal.etlsetup.domain.EtlSessionChatEntity;
import dev.zendal.etlsetup.dto.chat.EtlSessionChatDto;
import dev.zendal.etlsetup.exception.EntityNotFoundException;
import dev.zendal.etlsetup.mapper.EtlSessionChatMapper;
import dev.zendal.etlsetup.repository.EtlSessionChatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedModel;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EtlSessionChatFindServiceImpl implements EtlSessionChatFindService {

    private final EtlSessionChatRepository eetSessionChatRepository;

    private final EtlSessionChatMapper etlSessionChatMapper;

    @Override
    public PagedModel<EtlSessionChatDto> findBySessionId(UUID sessionId, Pageable pageable) {
        return new PagedModel<>(
                this.eetSessionChatRepository.findAllBySessionId(sessionId, pageable)
                        .map(this.etlSessionChatMapper::toDto)
        );
    }

    @Override
    public EtlSessionChatEntity getLast(UUID sessionId) {
        return this.eetSessionChatRepository.findAllBySessionId(
                sessionId,
                PageRequest.of(0, 1, Sort.by(Sort.Direction.DESC, "id"))
        ).getContent().stream().findFirst().orElseThrow(
                () -> new EntityNotFoundException("Can't find any chat at session " + sessionId)
        );
    }
}
