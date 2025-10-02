package dev.zendal.etlsetup.dto.source.s3;

import dev.zendal.etlsetup.dto.source.SourceSettingsType;
import dev.zendal.etlsetup.dto.source.raw.XmlSourceSettings;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class XmlS3SourceSettings implements S3SourceSettings, XmlSourceSettings {

    @Size(min = 1)
    private List<String> s3Paths;

    @NotNull
    private String rootTag;

    @Override
    public SourceSettingsType type() {
        return SourceSettingsType.XML;
    }
}
