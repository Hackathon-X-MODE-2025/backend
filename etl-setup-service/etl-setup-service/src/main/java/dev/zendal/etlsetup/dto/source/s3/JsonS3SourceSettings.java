package dev.zendal.etlsetup.dto.source.s3;

import dev.zendal.etlsetup.dto.source.SourceSettingsType;
import dev.zendal.etlsetup.dto.source.raw.JsonSourceSettings;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class JsonS3SourceSettings implements S3SourceSettings, JsonSourceSettings {

    @Size(min = 1)
    private List<String> s3Paths;

    @Override
    public SourceSettingsType type() {
        return SourceSettingsType.JSON;
    }
}
