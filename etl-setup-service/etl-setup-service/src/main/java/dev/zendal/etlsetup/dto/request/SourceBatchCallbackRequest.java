package dev.zendal.etlsetup.dto.request;

import lombok.Data;

@Data
public class SourceBatchCallbackRequest {

    private boolean success;

    private String context;
}
