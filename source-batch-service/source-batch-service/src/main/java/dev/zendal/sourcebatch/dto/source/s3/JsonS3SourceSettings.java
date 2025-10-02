package dev.zendal.sourcebatch.dto.source.s3;

import dev.zendal.sourcebatch.dto.source.SourceSettingsType;
import dev.zendal.sourcebatch.dto.source.raw.JsonSourceSettings;
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
