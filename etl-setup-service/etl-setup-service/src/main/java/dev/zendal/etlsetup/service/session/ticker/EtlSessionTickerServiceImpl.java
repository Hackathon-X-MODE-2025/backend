package dev.zendal.etlsetup.service.session.ticker;

import dev.zendal.etlsetup.clinet.DataBasePredictionClient;
import dev.zendal.etlsetup.domain.EtlSessionEntity;
import dev.zendal.etlsetup.domain.EtlSessionStatus;
import dev.zendal.etlsetup.dto.databaseprediction.PerformanceRequirements;
import dev.zendal.etlsetup.dto.databaseprediction.RecommendRequest;
import dev.zendal.etlsetup.service.session.picker.EtlSessionPickerService;
import dev.zendal.etlsetup.service.session.status.EtlSessionStatusService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EtlSessionTickerServiceImpl implements EtlSessionTickerService {

    private final EtlSessionPickerService etlSessionPickerService;

    private final DataBasePredictionClient dataBasePredictionClient;

    private final EtlSessionStatusService etlSessionStatusService;

    @Override
    @Transactional
    public void tick() {
        this.etlSessionPickerService.pickByStatus(EtlSessionStatus.AI_DATABASE_ANALYZING).ifPresent(this::process);
    }

    private void process(EtlSessionEntity etlSessionEntity) {

        final var dataCharacteristics = etlSessionEntity.getSources().stream()
                .map(source -> Pair.of("scheme_" + source.getId(), source.getScheme()))
                .collect(Collectors.toMap(Pair::getLeft, Pair::getRight));

        final var request = RecommendRequest.builder()
                .useCase("Create best perform data scheme")
                .dataCharacteristics(dataCharacteristics)
                .performanceRequirements(
                        PerformanceRequirements.builder()
                                .expectedSizeInGB(etlSessionEntity.getExpectedSizeInGb())
                                .rateUpdateData(etlSessionEntity.getUpdateRate().name())
                                .build()
                )
                .build();


        etlSessionEntity.setDataBasePrediction(this.dataBasePredictionClient.predict(etlSessionEntity.getId(),request));

        this.etlSessionStatusService.change(etlSessionEntity.getId(), EtlSessionStatus.USER_CHOOSE_DATABASE);
    }
}
