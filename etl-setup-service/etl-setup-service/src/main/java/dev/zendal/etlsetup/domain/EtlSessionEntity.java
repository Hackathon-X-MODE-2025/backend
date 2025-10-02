package dev.zendal.etlsetup.domain;

import dev.zendal.etlsetup.domain.source.DataBasePrediction;
import dev.zendal.etlsetup.domain.source.EtlSessionSourceEntity;
import dev.zendal.etlsetup.dto.datasource.DataSourceSettings;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.*;

@Getter
@Setter
@Entity
@Table(name = "etl_session")
@Accessors(chain = true)
@EntityListeners(AuditingEntityListener.class)
public class EtlSessionEntity {

    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 30)
    private EtlSessionStatus status;

    @NotNull
    @Column(name = "expected_size_in_gb", nullable = false)
    private Integer expectedSizeInGb;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "scheduler_rate", nullable = false, length = 20)
    private SchedulerRateType schedulerRate;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "update_rate", nullable = false, length = 20)
    private UpdateRateType updateRate;


    @Column(name = "data_base_prediction")
    @JdbcTypeCode(SqlTypes.JSON)
    private DataBasePrediction dataBasePrediction;


    @Column(name = "data_source_settings")
    @JdbcTypeCode(SqlTypes.JSON)
    private DataSourceSettings dataSourceSettings;

    @Column(name = "airflow_id")
    private UUID airflowId;

    @NotNull
    @Column(name = "created_date", nullable = false, updatable = false)
    @CreatedDate
    private LocalDateTime createdDate;

    @NotNull
    @Column(name = "modified_date", nullable = false)
    @LastModifiedDate
    private LocalDateTime modifiedDate;

    @OneToMany(mappedBy = "etlSession", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<EtlSessionSourceEntity> sources = new LinkedHashSet<>();

    @OneToMany(mappedBy = "etlSession", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<EtlSessionChatEntity> chats = new LinkedHashSet<>();


    public EtlSessionEntity addAll(Collection<EtlSessionSourceEntity> files) {
        this.sources.addAll(files);
        files.forEach(file -> file.setEtlSession(this));
        return this;
    }

    public EtlSessionEntity addChat(EtlSessionChatEntity etlSessionChatEntity) {
        this.chats.add(etlSessionChatEntity);
        etlSessionChatEntity.setEtlSession(this);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        EtlSessionEntity that = (EtlSessionEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}