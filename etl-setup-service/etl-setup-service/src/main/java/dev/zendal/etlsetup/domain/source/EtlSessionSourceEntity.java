package dev.zendal.etlsetup.domain.source;

import dev.zendal.etlsetup.domain.EtlSessionEntity;
import dev.zendal.etlsetup.dto.source.SourceSettings;
import dev.zendal.etlsetup.dto.source.SourceSettingsType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@IdClass(EtlSessionSourceEntity.EtlSessionSourceId.class)
@Entity
@Table(name = "etl_session_source")
@Accessors(chain = true)
public class EtlSessionSourceEntity {

    @Id
    @NotNull
    @Column(name = "etl_session_id", nullable = false)
    private UUID etlSessionId;

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

    @MapsId
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "etl_session_id", nullable = false)
    private EtlSessionEntity etlSession;

    @NotNull
    @Column(name = "type", nullable = false, length = 30)
    @Enumerated(EnumType.STRING)
    private SourceSettingsType type;

    @NotNull
    @Column(name = "context", nullable = false)
    @JdbcTypeCode(SqlTypes.JSON)
    private SourceSettings context;

    @NotNull
    @Column(name = "status", nullable = false, length = 30)
    @Enumerated(EnumType.STRING)
    private EtlSessionSourceStatus status;

    @Column(name = "scheme", length = Integer.MAX_VALUE)
    private String scheme;


    public EtlSessionSourceEntity setEtlSession(EtlSessionEntity etlSession) {
        this.etlSession = etlSession;
        this.etlSessionId = etlSession.getId();
        return this;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode
    public static class EtlSessionSourceId implements Serializable {
        private UUID etlSessionId;

        private UUID id;

    }


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        EtlSessionSourceEntity that = (EtlSessionSourceEntity) o;
        return Objects.equals(etlSessionId, that.etlSessionId) && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(etlSessionId, id);
    }
}