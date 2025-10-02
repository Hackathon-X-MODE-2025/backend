package dev.zendal.etlsetup.service.codegenerator;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.zendal.etlsetup.clinet.CodeGeneratorClient;
import dev.zendal.etlsetup.domain.EtlSessionChatEntity;
import dev.zendal.etlsetup.domain.EtlSessionEntity;
import dev.zendal.etlsetup.domain.source.DataSourceType;
import dev.zendal.etlsetup.domain.source.EtlSessionSourceEntity;
import dev.zendal.etlsetup.dto.codegenerator.CodeGeneratorRequest;
import dev.zendal.etlsetup.dto.codegenerator.CodeGeneratorResponse;
import dev.zendal.etlsetup.dto.codegenerator.CodeGeneratorSchema;
import dev.zendal.etlsetup.dto.codegenerator.DagCodeGeneratorRequest;
import dev.zendal.etlsetup.dto.datasource.DataSourceSettings;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class CodeGeneratorServiceImpl implements CodeGeneratorService {

    private final ObjectMapper objectMapper;

    private final CodeGeneratorClient codeGeneratorClient;

    @Override
    public CodeGeneratorResponse create(EtlSessionEntity etlSessionEntity) {
        return this.codeGeneratorClient.generateCode(this.generateRequest(etlSessionEntity));

    }

    @Override
    public String regenerateDag(EtlSessionChatEntity chat) {
        final var request = this.generateRequest(chat.getEtlSession());

        final var targetRequest = new DagCodeGeneratorRequest();

        targetRequest
                .setDdlResults(chat.getDdl())
                .setDagTemplate(request.getDagTemplate())
                .setTargetDb(request.getTargetDb())
                .setSourceType(request.getSourceType())
                .setSchemaInput(request.getSchemaInput());

        return this.codeGeneratorClient.regenerateDag(targetRequest);
    }

    private CodeGeneratorRequest generateRequest(EtlSessionEntity etlSessionEntity) {
        final var output = etlSessionEntity.getDataSourceSettings();
        return CodeGeneratorRequest.builder()
                .targetDb(this.process(output))
                .dagTemplate(this.process(output.type(), etlSessionEntity))
                .sourceType("JsonHDFSSourceSettings")
                .schemaInput(
                        CodeGeneratorSchema.builder()
                                .inputs(etlSessionEntity.getSources().stream()
                                        .map(v ->
                                                {
                                                    try {
                                                        return CodeGeneratorSchema.CodeGeneratorSchemaSettings.builder()
                                                                .schema(this.objectMapper.readValue(v.getScheme(), new TypeReference<Map<String, Object>>() {
                                                                }))
                                                                .settings(v.getContext())
                                                                .build();
                                                    } catch (JsonProcessingException e) {
                                                       return CodeGeneratorSchema.CodeGeneratorSchemaSettings.builder()
                                                               .schema(v.getScheme())
                                                               .settings(v.getContext())
                                                               .build();
                                                    }
                                                }
                                        ).toList()
                                )
                                .output(output)
                                .processingRequirements(
                                        Map.of(
                                                "incremental_loading", true,
                                                "data_validation", true,
                                                "error_handling", "stop_on_failure",
                                                "performance_optimization", true,
                                                "data_retention", "30 days"
                                        )
                                )
                                .build()
                )
                .build();
    }


    private String process(DataSourceSettings output) {
        if (output.type() == DataSourceType.POSTGRES) {
            return "postgres";
        }
        if (output.type() == DataSourceType.CLICK_HOUSE) {
            return "clickhouse";
        }

        return "postgres";
    }

    private String process(DataSourceType type, EtlSessionEntity etlSessionEntity) {
        switch (type) {
            case POSTGRES -> {
                return "spark_hdfs_postgres";
            }
            case CLICK_HOUSE -> {
                return "spark_hdfs_clickhouse";
            }
        }
        return "";
    }
}
