package dev.zendal.etlsetup.service.session;

import dev.zendal.etlsetup.repository.EtlSessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SessionProcessService {

    private final EtlSessionRepository etlSessionService;

    @Async
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void set(UUID sessionId, int process) {
        this.etlSessionService.findById(sessionId).ifPresent(session -> {
            session.setProcess(process);
        });
    }
}
