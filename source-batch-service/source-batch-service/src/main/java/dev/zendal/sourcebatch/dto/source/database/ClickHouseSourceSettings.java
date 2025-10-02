package dev.zendal.sourcebatch.dto.source.database;

import dev.zendal.sourcebatch.dto.source.SourceSettings;
import dev.zendal.sourcebatch.dto.source.SourceSettingsType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ClickHouseSourceSettings implements SourceSettings {

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

    @Override
    public SourceSettingsType type() {
        return SourceSettingsType.CLICK_HOUSE;
    }
}
