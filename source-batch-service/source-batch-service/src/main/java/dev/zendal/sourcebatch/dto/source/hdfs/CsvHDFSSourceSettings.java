package dev.zendal.sourcebatch.dto.source.hdfs;

import dev.zendal.sourcebatch.dto.source.SourceSettingsType;
import dev.zendal.sourcebatch.dto.source.raw.CsvSourceSettings;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class CsvHDFSSourceSettings implements HDFSSourceSettings, CsvSourceSettings {

    private List<String> paths;

    @Size(min = 1, max = 1)
    @NotNull
    private String delimiter;


    @Override
    public SourceSettingsType type() {
        return SourceSettingsType.CSV;
    }
}
