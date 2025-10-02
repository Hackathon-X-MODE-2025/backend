package dev.zendal.etlsetup.repository;

import dev.zendal.etlsetup.domain.source.EtlSessionSourceEntity;
import dev.zendal.etlsetup.domain.source.EtlSessionSourceStatus;
import jakarta.persistence.LockModeType;
import jakarta.persistence.QueryHint;
import org.hibernate.cfg.AvailableSettings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.QueryHints;

import java.util.Optional;
import java.util.UUID;

public interface EtlSessionSourceRepository extends JpaRepository<EtlSessionSourceEntity, Long> {

    Optional<EtlSessionSourceEntity> findTopOneByStatus(EtlSessionSourceStatus status);


    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @QueryHints(@QueryHint(name = AvailableSettings.JAKARTA_LOCK_TIMEOUT, value = "-2"))
    Optional<EtlSessionSourceEntity> findOneById(UUID id);


    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<EtlSessionSourceEntity> getOneById(UUID id);
}
