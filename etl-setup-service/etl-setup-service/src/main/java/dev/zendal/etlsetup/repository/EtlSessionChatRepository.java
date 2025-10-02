package dev.zendal.etlsetup.repository;

import dev.zendal.etlsetup.domain.EtlSessionChatEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EtlSessionChatRepository extends JpaRepository<EtlSessionChatEntity, Long> {

    Page<EtlSessionChatEntity> findAllBySessionId(UUID sessionId, Pageable pageable);
}
