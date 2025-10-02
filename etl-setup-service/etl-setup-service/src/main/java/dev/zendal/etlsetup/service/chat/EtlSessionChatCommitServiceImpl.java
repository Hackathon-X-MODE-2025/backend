package dev.zendal.etlsetup.service.chat;

import dev.zendal.etlsetup.clinet.AirFlowAdapterClient;
import dev.zendal.etlsetup.clinet.SourceBatchClient;
import dev.zendal.etlsetup.domain.EtlSessionStatus;
import dev.zendal.etlsetup.domain.source.DataSourceType;
import dev.zendal.etlsetup.dto.source.DataBaseInitRequest;
import dev.zendal.etlsetup.service.session.status.EtlSessionStatusService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class EtlSessionChatCommitServiceImpl implements EtlSessionChatCommitService {

    private final EtlSessionChatFindService etlSessionChatFindService;

    private final AirFlowAdapterClient airFlowAdapterClient;

    private final EtlSessionStatusService etlSessionStatusService;

    private final SourceBatchClient sourceBatchClient;

    @Override
    @Transactional
    public void commit(UUID sessionId) {
        final var lastOne = this.etlSessionChatFindService.getLast(sessionId);

        if (lastOne.getEtlSession().getDataSourceSettings().type() != DataSourceType.HDFS) {
            log.info("Init database");

            //TODO...
//            this.sourceBatchClient.initDataBase(
//                    DataBaseInitRequest.builder()
//                            .dataSourceSettings(lastOne.getEtlSession().getDataSourceSettings())
//                            //.ddl(lastOne.getDdl())
//                            .build()
//            );
        }
        this.airFlowAdapterClient.saveDag(sessionId, lastOne.getDag());
        this.etlSessionStatusService.change(sessionId, EtlSessionStatus.ETL_CREATION);
        this.etlSessionStatusService.change(sessionId, EtlSessionStatus.FINISHED);
    }
}
