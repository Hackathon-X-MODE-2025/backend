package dev.zendal.etlsetup.dto.source.hdfs;

import dev.zendal.etlsetup.dto.source.SourceSettingsType;
import dev.zendal.etlsetup.dto.source.raw.XmlSourceSettings;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class XmlHDFSSourceSettings implements HDFSSourceSettings, XmlSourceSettings {

    private List<String> paths;

    @NotNull
    private String rootTag;

    @Override
    public SourceSettingsType type() {
        return SourceSettingsType.XML;
    }
}
