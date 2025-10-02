package dev.zendal.etlsetup.dto.chat;

import dev.zendal.etlsetup.dto.codegenerator.PipelineResponse;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
public class EtlSessionChatDto {

    private Long id;

    private List<PipelineResponse.DDLResult> ddl;

    private String dag;

    private Map<Object, Object> pipelineVisualization;

    private LocalDateTime createdDate;

    private LocalDateTime modifiedDate;
}
