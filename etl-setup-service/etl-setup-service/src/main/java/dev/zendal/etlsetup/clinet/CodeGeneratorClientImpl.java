package dev.zendal.etlsetup.clinet;

import dev.zendal.etlsetup.dto.codegenerator.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class CodeGeneratorClientImpl implements CodeGeneratorClient {

    private final WebClient codeGeneratorWebClient;

    @Override
    public CodeGeneratorResponse generateCode(CodeGeneratorRequest codeGeneratorRequest) {
        final var job = this.codeGeneratorWebClient.post()
                .uri(uriBuilder -> uriBuilder.path("/orchestrate").build())
                .bodyValue(codeGeneratorRequest)
                .retrieve()
                .bodyToMono(CodeGeneratorJobResponse.class)
                .block().getJobId();


        log.info("Job id: {}", job);
        PipelineResponse pipeline;
        do {
            pipeline = this.codeGeneratorWebClient.get()
                    .uri(uriBuilder -> uriBuilder.path("/orchestration-job-status/{jobId}").build(job))
                    .retrieve()
                    .bodyToMono(PipelineResponse.class)
                    .block();
            try {
                Thread.sleep(5 * 1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        } while ("processing".equalsIgnoreCase(Optional.ofNullable(pipeline.getStatus()).orElse("")));

        return CodeGeneratorResponse.builder()
                .ddlResults(pipeline.getDdlResults())
                .dagCode(pipeline.getDagCode())
                .pipelineVisualization(pipeline.getPipelineVisualization())
                .build();
    }


    @Override
    public String regenerateDag(DagCodeGeneratorRequest codeGeneratorRequest) {
        final var job = this.codeGeneratorWebClient.post()
                .uri(uriBuilder -> uriBuilder.path("/generate-dag").build())
                .bodyValue(codeGeneratorRequest)
                .retrieve()
                .bodyToMono(CodeGeneratorJobResponse.class)
                .block().getJobId();


        log.info("Job id: {}", job);
        PipelineResponse pipeline;
        do {
            pipeline = this.codeGeneratorWebClient.get()
                    .uri(uriBuilder -> uriBuilder.path("/dag-job-status/{jobId}").build(job))
                    .retrieve()
                    .bodyToMono(PipelineResponse.class)
                    .block();

            try {
                Thread.sleep(5 * 1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        } while ("processing".equalsIgnoreCase(Optional.ofNullable(pipeline.getStatus()).orElse("")));

        return pipeline.getDagCode();
    }
}
