package dev.zendal.etlsetup.dto.response;

import dev.zendal.etlsetup.domain.EtlSessionStatus;
import dev.zendal.etlsetup.domain.source.DataBasePrediction;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
public class EtlSessionDto {

    private UUID id;

    private EtlSessionStatus status;

    private UUID airflowId;

    private List<EtlSessionSourceDto> sources;

    private DataBasePrediction dataBasePrediction;


    private LocalDateTime createdDate;
}
