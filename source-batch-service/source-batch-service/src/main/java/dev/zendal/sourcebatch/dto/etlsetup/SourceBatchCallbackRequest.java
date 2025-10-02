package dev.zendal.sourcebatch.dto.etlsetup;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class SourceBatchCallbackRequest {

    private boolean success;

    private String context;
}
