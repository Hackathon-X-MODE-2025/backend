package dev.zendal.airflowadapter.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class AirFlowDagCreationRequest {

    @NotNull
    private UUID fallbackId;

    @NotNull
    private String dagCode;
}
