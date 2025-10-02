package dev.zendal.etlsetup.service.session.picker;

import dev.zendal.etlsetup.domain.EtlSessionEntity;
import dev.zendal.etlsetup.domain.EtlSessionStatus;
import dev.zendal.etlsetup.exception.EntityNotFoundException;
import dev.zendal.etlsetup.repository.EtlSessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EtlSessionPickerServiceImpl implements EtlSessionPickerService {

    private final EtlSessionRepository eetSessionRepository;

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public EtlSessionEntity pickById(UUID id) {
        return this.eetSessionRepository.getOneById(id).orElseThrow(
                () -> new EntityNotFoundException("Could not find EtlSession with id " + id)
        );
    }


    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public Optional<EtlSessionEntity> pickByStatus(EtlSessionStatus status) {
        return this.eetSessionRepository.findTop1ByStatus(status)
                .map(EtlSessionEntity::getId)
                .map(this::pickById);
    }
}
