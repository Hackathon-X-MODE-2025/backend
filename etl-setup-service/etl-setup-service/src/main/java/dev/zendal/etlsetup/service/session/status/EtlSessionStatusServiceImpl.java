package dev.zendal.etlsetup.service.session.status;

import dev.zendal.etlsetup.domain.EtlSessionStatus;
import dev.zendal.etlsetup.service.session.picker.EtlSessionPickerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class EtlSessionStatusServiceImpl implements EtlSessionStatusService {

    private static final Map<EtlSessionStatus, Set<EtlSessionStatus>> STATUS_FLOW = Map.of(
            EtlSessionStatus.ANALYZING, Set.of(EtlSessionStatus.AI_DATABASE_ANALYZING, EtlSessionStatus.ERROR),
            EtlSessionStatus.AI_DATABASE_ANALYZING, Set.of(EtlSessionStatus.USER_CHOOSE_DATABASE, EtlSessionStatus.ERROR),
            EtlSessionStatus.USER_CHOOSE_DATABASE, Set.of(EtlSessionStatus.AI_ETL_ANALYZING),
            EtlSessionStatus.AI_ETL_ANALYZING, Set.of(EtlSessionStatus.USER_WAITING),
            EtlSessionStatus.USER_WAITING, Set.of(EtlSessionStatus.AI_ETL_ANALYZING, EtlSessionStatus.ETL_CREATION),
            EtlSessionStatus.ETL_CREATION, Set.of(EtlSessionStatus.FINISHED, EtlSessionStatus.ERROR)
    );

    private final EtlSessionPickerService etlSessionPickerService;

    @Override
    public void change(UUID sessionId, EtlSessionStatus newStatus) {
        final var session = this.etlSessionPickerService.pickById(sessionId);

        if (!STATUS_FLOW.getOrDefault(session.getStatus(), Set.of()).contains(newStatus)) {
            throw new IllegalStateException(String.format("Session status %s not found from %s", newStatus, session.getStatus()));
        }

        log.info("Changing status of session {} to {} at {}", session.getStatus(), newStatus, sessionId);
        //TODO Changing from to
        session.setStatus(newStatus);
    }
}
