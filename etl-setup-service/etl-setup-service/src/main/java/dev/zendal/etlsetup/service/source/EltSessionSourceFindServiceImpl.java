package dev.zendal.etlsetup.service.source;

import dev.zendal.etlsetup.domain.source.EtlSessionSourceEntity;
import dev.zendal.etlsetup.domain.source.EtlSessionSourceStatus;
import dev.zendal.etlsetup.repository.EtlSessionSourceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EltSessionSourceFindServiceImpl implements EltSessionSourceFindService {

    private final EtlSessionSourceRepository etlSessionSourceRepository;

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
    public Optional<UUID> findOneByStatus(EtlSessionSourceStatus status) {
        return this.etlSessionSourceRepository.findTopOneByStatus(status).map(EtlSessionSourceEntity::getId);
    }
}
