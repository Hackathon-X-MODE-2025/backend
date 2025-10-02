package dev.zendal.sourcebatch.dto.source.hdfs;

import dev.zendal.sourcebatch.dto.source.SourceSettingsType;
import dev.zendal.sourcebatch.dto.source.raw.JsonSourceSettings;
import lombok.Data;

import java.util.List;

@Data
public class JsonHDFSSourceSettings implements HDFSSourceSettings, JsonSourceSettings {

    private List<String> paths;

    @Override
    public SourceSettingsType type() {
        return SourceSettingsType.JSON;
    }
}
