package dev.zendal.etlsetup.dto.source.hdfs;

import dev.zendal.etlsetup.dto.source.SourceSettingsType;
import dev.zendal.etlsetup.dto.source.raw.JsonSourceSettings;
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
