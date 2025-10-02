package dev.zendal.etlsetup.dto.source.database;

import dev.zendal.etlsetup.dto.source.SourceSettings;
import dev.zendal.etlsetup.dto.source.SourceSettingsType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PostgreSQLSourceSettings implements SourceSettings {

    @NotNull
    private String host;

    @Min(1)
    @NotNull
    private Integer port;

    @NotNull
    private String username;

    @NotNull
    private String password;

    @NotNull
    private String database;

    @NotNull
    private String schema;


    @Override
    public SourceSettingsType type() {
        return SourceSettingsType.POSTGRES;
    }
}
