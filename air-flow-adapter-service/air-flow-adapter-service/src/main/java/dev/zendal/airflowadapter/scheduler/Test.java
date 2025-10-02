package dev.zendal.airflowadapter.scheduler;


import com.google.common.base.Strings;
import dev.zendal.airflowadapter.client.EtlSetupClient;
import dev.zendal.airflowadapter.domain.AirFlowCopyDagTaskEntity;
import dev.zendal.airflowadapter.repository.AirFlowCopyDagRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatusCode;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class Test {

    private final AirFlowCopyDagRepository airFlowCopyDagRepository;

    private final WebClient airflowWebClient;

    private final EtlSetupClient etlSetupClient;

    @Scheduled(fixedRate = 1000)
    @Transactional
    void isCompleted() {
        this.airFlowCopyDagRepository.findTop1ByCompletedIsFalseOrderByModifiedDateAsc().ifPresent(this::process);
    }

    private void process(@NotNull AirFlowCopyDagTaskEntity airFlowCopyDagTaskEntity) {
        airFlowCopyDagTaskEntity.setModifiedDate(LocalDateTime.now());
        final var dif = LocalDateTime.now().minusMinutes(5);
        if (airFlowCopyDagTaskEntity.getCreatedDate().isBefore(dif)) {
            try {
                this.etlSetupClient.notify(airFlowCopyDagTaskEntity.getFallbackId(), false, null);
            }catch (Exception e){
                log.error("Failed to notify fallbackId: {}", airFlowCopyDagTaskEntity.getFallbackId(), e);
            }
            airFlowCopyDagTaskEntity.setCompleted(true);
            return;
        }

        final var result = this.airflowWebClient.get()
                .uri("api/v2/dags/" + airFlowCopyDagTaskEntity.getId())
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> Mono.empty())
                .bodyToMono(String.class)
                .onErrorComplete()
                .block();

        if (Strings.isNullOrEmpty(result) || result.contains("was not found")) {
            return;
        }
        log.info("Dag completed for dagId: {}", result);
        try {
            this.etlSetupClient.notify(airFlowCopyDagTaskEntity.getFallbackId(), true, airFlowCopyDagTaskEntity.getId());
        }catch (Exception e){
            log.error("Failed to notify fallbackId: {}", airFlowCopyDagTaskEntity.getFallbackId(), e);
        }
        airFlowCopyDagTaskEntity.setCompleted(true);
    }

}
