package dev.zendal.etlsetup.dto.response;

import dev.zendal.etlsetup.domain.source.DataBasePrediction;
import dev.zendal.etlsetup.domain.source.EtlSessionSourceStatus;
import dev.zendal.etlsetup.dto.source.SourceSettings;
import dev.zendal.etlsetup.dto.source.SourceSettingsType;
import lombok.Data;

import java.util.UUID;

@Data
public class EtlSessionSourceDto {

    private UUID id;

    private SourceSettings context;

    private SourceSettingsType type;

    private EtlSessionSourceStatus status;

    private String scheme;
}
