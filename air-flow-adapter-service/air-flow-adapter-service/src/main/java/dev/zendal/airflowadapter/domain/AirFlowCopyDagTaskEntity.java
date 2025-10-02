package dev.zendal.airflowadapter.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "air_flow_copy_dag_task")
@Accessors(chain = true)
@EntityListeners(AuditingEntityListener.class)
public class AirFlowCopyDagTaskEntity {

    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @NotNull
    @Column(name = "fallback_id", nullable = false)
    private UUID fallbackId;

    @NotNull
    @Column(name = "completed", nullable = false)
    private Boolean completed = false;

    @Size(max = 300)
    @NotNull
    @Column(name = "repository_location", nullable = false, length = 300)
    private String repositoryLocation;

    @NotNull
    @Column(name = "created_date", nullable = false, updatable = false)
    @CreatedDate
    private LocalDateTime createdDate;

    @NotNull
    @Column(name = "modified_date", nullable = false)
    @LastModifiedDate
    private LocalDateTime modifiedDate;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        AirFlowCopyDagTaskEntity that = (AirFlowCopyDagTaskEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}