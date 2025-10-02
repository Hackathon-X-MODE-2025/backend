package dev.zendal.etlsetup.scheduler;

import dev.zendal.etlsetup.service.source.EtlSessionSourceProcessingTicker;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EtlSessionSourceScheduler {

    private final EtlSessionSourceProcessingTicker etlSessionSourceProcessingTicker;

    @Scheduled(fixedRate = 1000)
    void shouldProcess() {
        this.etlSessionSourceProcessingTicker.find();
    }
}
