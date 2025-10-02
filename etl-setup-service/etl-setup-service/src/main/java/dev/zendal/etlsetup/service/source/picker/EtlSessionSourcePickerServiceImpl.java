package dev.zendal.etlsetup.service.source.picker;

import dev.zendal.etlsetup.domain.source.EtlSessionSourceEntity;
import dev.zendal.etlsetup.domain.source.EtlSessionSourceStatus;
import dev.zendal.etlsetup.repository.EtlSessionSourceRepository;
import dev.zendal.etlsetup.service.source.EltSessionSourceFindService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EtlSessionSourcePickerServiceImpl implements EtlSessionSourcePickerService {

    private final EtlSessionSourceRepository etlSessionSourceRepository;

    private final EltSessionSourceFindService eltSessionSourceFindService;

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public EtlSessionSourceEntity pickById(UUID sourceId) {
        return this.etlSessionSourceRepository.getOneById(sourceId).orElseThrow(
                () -> new RuntimeException("Can't pick session source by: " + sourceId)
        );
    }

    @Override
    public Optional<EtlSessionSourceEntity> pickByStatus(EtlSessionSourceStatus status) {
        return this.eltSessionSourceFindService.findOneByStatus(status)
                .flatMap(this.etlSessionSourceRepository::findOneById);
    }
}
