package dev.zendal.sourcebatch.service.handler;

import dev.zendal.sourcebatch.dto.source.SourceSettings;
import dev.zendal.sourcebatch.dto.source.SourceSettingsType;

public interface SourceBatchHandler {

    String process(SourceSettings sourceSettings);

    SourceSettingsType type();
}
