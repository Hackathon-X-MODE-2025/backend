package dev.zendal.airflowadapter.repository;

import dev.zendal.airflowadapter.domain.AirFlowCopyDagTaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AirFlowCopyDagRepository extends JpaRepository<AirFlowCopyDagTaskEntity, UUID> {

    Optional<AirFlowCopyDagTaskEntity> findTop1ByCompletedIsFalseOrderByModifiedDateAsc();
}
