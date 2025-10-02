package dev.zendal.etlsetup.service.session;

import dev.zendal.etlsetup.dto.response.EtlSessionDto;
import dev.zendal.etlsetup.dto.response.EtlSessionShortDto;
import dev.zendal.etlsetup.exception.EntityNotFoundException;
import dev.zendal.etlsetup.mapper.EtlSessionMapper;
import dev.zendal.etlsetup.repository.EtlSessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EtlSessionFindServiceImpl implements EtlSessionFindService {

    private final EtlSessionRepository etlSessionRepository;

    private final EtlSessionMapper etlSessionMapper;

    @Override
    @Transactional(readOnly = true)
    public PagedModel<EtlSessionShortDto> findAll(Pageable pageable) {
        return new PagedModel<>(this.etlSessionRepository.findAll(pageable).map(this.etlSessionMapper::toShort));
    }

    @Override
    @Transactional(readOnly = true)
    public EtlSessionDto findById(UUID id) {
        return this.etlSessionRepository.findById(id)
                .map(this.etlSessionMapper::from)
                .orElseThrow(
                        () -> new EntityNotFoundException("Can't find session at id: " + id)
                );
    }
}
