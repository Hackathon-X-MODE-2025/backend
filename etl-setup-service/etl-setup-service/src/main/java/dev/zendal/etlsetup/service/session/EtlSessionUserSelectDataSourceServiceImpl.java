package dev.zendal.etlsetup.service.session;

import dev.zendal.etlsetup.domain.EtlSessionStatus;
import dev.zendal.etlsetup.dto.datasource.DataSourceSettings;
import dev.zendal.etlsetup.service.session.picker.EtlSessionPickerService;
import dev.zendal.etlsetup.service.session.status.EtlSessionStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EtlSessionUserSelectDataSourceServiceImpl implements EtlSessionUserSelectDataSourceService {

    private final EtlSessionPickerService etlSessionPickerService;

    private final EtlSessionStatusService etlSessionStatusService;

    @Override
    @Transactional
    public void selectDataSource(UUID sessionId, DataSourceSettings dataSourceSettings) {
        this.etlSessionPickerService.pickById(sessionId).setDataSourceSettings(dataSourceSettings);
        this.etlSessionStatusService.change(sessionId, EtlSessionStatus.AI_ETL_ANALYZING);
    }
}
