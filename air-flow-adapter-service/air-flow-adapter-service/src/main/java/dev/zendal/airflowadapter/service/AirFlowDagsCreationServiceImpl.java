package dev.zendal.airflowadapter.service;

import dev.zendal.airflowadapter.domain.AirFlowCopyDagTaskEntity;
import dev.zendal.airflowadapter.dto.request.AirFlowDagCreationRequest;
import dev.zendal.airflowadapter.repository.AirFlowCopyDagRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AirFlowDagsCreationServiceImpl implements AirFlowDagsCreationService {

    private static final String DAGS_FOLDER = "dags";

    private final GitService gitService;

    private final AirFlowDagPatcherService airFlowDagPatcherService;

    private final AirFlowCopyDagRepository airFlowDagRepository;

    @Override
    @Transactional
    public void create(AirFlowDagCreationRequest request) {
        try (final var git = this.gitService.getGit()) {
            safeCommit(git, request);
        } catch (GitAPIException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }

    }

    private void safeCommit(Git git, AirFlowDagCreationRequest request) throws GitAPIException {
        final var dir = git.getRepository().getDirectory().getParentFile();
        log.info("Pulling...");
        git.pull().call();

        final var dagsDir = new File(dir, DAGS_FOLDER);

        dagsDir.mkdirs();

        final var requestId = UUID.randomUUID();

        final var targetFile = requestId + ".py";

        final var patched = this.airFlowDagPatcherService.insertDagId(request.getDagCode(), requestId.toString()).getBytes(StandardCharsets.UTF_8);
        try {
            log.info("Writing dag id {} to {}", requestId, new File(dagsDir, targetFile));
            Files.write(new File(dagsDir, targetFile).toPath(), patched);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }

        final var location = DAGS_FOLDER + "/" + targetFile;

        this.airFlowDagRepository.save(
                new AirFlowCopyDagTaskEntity()
                        .setId(requestId)
                        .setFallbackId(request.getFallbackId())
                        .setRepositoryLocation(location)
                        .setCompleted(false)
        );

        git.add()
                .addFilepattern(location)
                .call();
        git.commit()
                .setAuthor("Airflow Bot", "airflow@example.com")
                .setMessage("Added DAG by " + request.getFallbackId() + ", size: " + patched.length)
                .call();
        git.push().call();
    }
}
