package dev.zendal.etlsetup.dto.source.s3;

import dev.zendal.etlsetup.dto.source.SourceSettingsType;
import dev.zendal.etlsetup.dto.source.raw.CsvSourceSettings;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class CsvS3SourceSettings implements S3SourceSettings, CsvSourceSettings {

    @Size(min = 1, max = 1)
    @NotNull
    private String delimiter;

    @Size(min = 1)
    private List<String> s3Paths;

    @Override
    public SourceSettingsType type() {
        return SourceSettingsType.CSV;
    }
}
