package dev.zendal.etlsetup.service.session.ticker;

import dev.zendal.etlsetup.domain.EtlSessionChatEntity;
import dev.zendal.etlsetup.domain.EtlSessionEntity;
import dev.zendal.etlsetup.domain.EtlSessionStatus;
import dev.zendal.etlsetup.service.codegenerator.CodeGeneratorService;
import dev.zendal.etlsetup.service.session.picker.EtlSessionPickerService;
import dev.zendal.etlsetup.service.session.status.EtlSessionStatusService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class EtlSessionAiEtlAnalyzingTickerImpl implements EtlSessionAiEtlAnalyzingTicker {

    private final EtlSessionPickerService etlSessionPickerService;

    private final EtlSessionStatusService etlSessionStatusService;

    private final CodeGeneratorService codeGeneratorService;

    @Override
    @Transactional
    public void tick() {
        this.etlSessionPickerService.pickByStatus(EtlSessionStatus.AI_ETL_ANALYZING).ifPresent(this::process);
    }

    private void process(EtlSessionEntity etlSessionEntity) {

        final var result = this.codeGeneratorService.create(etlSessionEntity);
        etlSessionEntity.addChat(
                new EtlSessionChatEntity()
                        .setDdl(result.getDdlResults())
                        .setPipelineVisualization(result.getPipelineVisualization())
                        .setDag(result.getDagCode())
        );

        this.etlSessionStatusService.change(etlSessionEntity.getId(), EtlSessionStatus.USER_WAITING);
    }
}
