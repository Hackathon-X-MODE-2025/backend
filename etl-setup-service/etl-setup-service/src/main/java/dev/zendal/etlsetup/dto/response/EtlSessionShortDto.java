package dev.zendal.etlsetup.dto.response;

import dev.zendal.etlsetup.domain.EtlSessionStatus;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class EtlSessionShortDto {

    private UUID id;

    private EtlSessionStatus status;

    private LocalDateTime createdDate;
}
