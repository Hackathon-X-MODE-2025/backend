package dev.zendal.etlsetup.scheduler;

import dev.zendal.etlsetup.service.session.ticker.EtlSessionAiEtlAnalyzingTicker;
import dev.zendal.etlsetup.service.session.ticker.EtlSessionTickerService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EtlSessionScheduler {

    private final EtlSessionTickerService etlSessionTickerService;

    private final EtlSessionAiEtlAnalyzingTicker etlSessionAiEtlAnalyzingTicker;


    @Scheduled(fixedDelay = 1000)
    void should() {
        this.etlSessionTickerService.tick();
    }

    @Scheduled(fixedDelay = 1000)
    void shouldEtlAnalyzing() {
        this.etlSessionAiEtlAnalyzingTicker.tick();
    }
}
