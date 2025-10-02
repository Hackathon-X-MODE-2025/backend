package dev.zendal.etlsetup.service.session;

import dev.zendal.etlsetup.domain.EtlSessionEntity;
import dev.zendal.etlsetup.dto.response.EtlSessionDto;
import dev.zendal.etlsetup.mapper.EtlSessionMapper;
import dev.zendal.etlsetup.repository.EtlSessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EtlSessionServiceImpl implements EtlSessionService {

    private final EtlSessionRepository etlSessionRepository;

    private final EtlSessionMapper etlSessionMapper;

    @Override
    @Transactional
    public EtlSessionDto create(EtlSessionEntity entity) {
        return this.etlSessionMapper.from(this.etlSessionRepository.save(entity));
    }
}
