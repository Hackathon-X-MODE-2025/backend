package dev.zendal.etlsetup.repository;

import dev.zendal.etlsetup.domain.EtlSessionEntity;
import dev.zendal.etlsetup.domain.EtlSessionStatus;
import jakarta.persistence.LockModeType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import java.util.Optional;
import java.util.UUID;

public interface EtlSessionRepository extends JpaRepository<EtlSessionEntity, UUID> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<EtlSessionEntity> getOneById(UUID id);

    Optional<EtlSessionEntity> findTop1ByStatus(EtlSessionStatus status);


    Page<EtlSessionEntity> findAllByUser_Id(UUID userId, Pageable pageable);
}
