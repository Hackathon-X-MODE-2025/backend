package dev.zendal.etlsetup.domain;

import dev.zendal.etlsetup.dto.codegenerator.PipelineResponse;
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
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "etl_session_chat")
@Accessors(chain = true)
@EntityListeners(AuditingEntityListener.class)
public class EtlSessionChatEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "etl_session_chat_id_gen")
    @SequenceGenerator(name = "etl_session_chat_id_gen", sequenceName = "etl_session_chat_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "etl_session_id")
    private EtlSessionEntity etlSession;

    @NotNull
    @Column(name = "etl_session_id", nullable = false, updatable = false, insertable = false)
    private UUID sessionId;

    @Column(name = "ddl")
    @JdbcTypeCode(SqlTypes.JSON)
    private List<PipelineResponse.DDLResult> ddl;

    @NotNull
    @Column(name = "dag", nullable = false, length = Integer.MAX_VALUE)
    private String dag;

    @Column(name = "pipeline_visualization")
    @JdbcTypeCode(SqlTypes.JSON)
    private Map<Object, Object> pipelineVisualization;

    @NotNull
    @Column(name = "created_date", nullable = false, updatable = false)
    @CreatedDate
    private LocalDateTime createdDate;

    @NotNull
    @Column(name = "modified_date", nullable = false)
    @LastModifiedDate
    private LocalDateTime modifiedDate;

    public EtlSessionChatEntity setEtlSession(EtlSessionEntity etlSession) {
        this.etlSession = etlSession;
        this.sessionId = etlSession.getId();
        return this;
    }
}