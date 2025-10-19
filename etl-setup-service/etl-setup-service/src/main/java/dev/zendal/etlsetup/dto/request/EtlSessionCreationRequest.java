package dev.zendal.etlsetup.dto.request;

import dev.zendal.etlsetup.domain.SchedulerRateType;
import dev.zendal.etlsetup.domain.UpdateRateType;
import dev.zendal.etlsetup.dto.source.SourceSettings;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.UUID;

@Data
@Accessors(chain = true)
public class EtlSessionCreationRequest {

    @NotNull
    private UUID userId;

    @NotEmpty
    private List<? extends SourceSettings> sourceSettings;

    @NotNull
    private Integer expectedSizeInGB;

    @NotNull
    private UpdateRateType updateRate;

    @NotNull
    private SchedulerRateType schedulerRate;


}
