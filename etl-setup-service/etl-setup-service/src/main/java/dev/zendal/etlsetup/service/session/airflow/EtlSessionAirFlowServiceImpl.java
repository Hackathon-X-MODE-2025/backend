package dev.zendal.etlsetup.service.session.airflow;

import dev.zendal.etlsetup.domain.EtlSessionStatus;
import dev.zendal.etlsetup.service.session.picker.EtlSessionPickerService;
import dev.zendal.etlsetup.service.session.status.EtlSessionStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EtlSessionAirFlowServiceImpl implements EtlSessionAirFlowService {

    private final EtlSessionPickerService etlSessionPickerService;
    private final EtlSessionStatusService etlSessionStatusService;

    @Override
    @Transactional
    public void acceptCallback(UUID sessionId, boolean success, UUID airflowId) {
        this.etlSessionPickerService.pickById(sessionId).setAirflowId(airflowId);
        this.etlSessionStatusService.change(sessionId, success ? EtlSessionStatus.FINISHED : EtlSessionStatus.ERROR);
    }
}
