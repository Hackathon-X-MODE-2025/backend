package dev.zendal.etlsetup.dto.airflow;

import lombok.Data;

import java.util.UUID;

@Data
public class AirFlowCallbackRequest {

    public boolean success;
    public UUID airflowId;
}
