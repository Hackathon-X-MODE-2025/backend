package dev.zendal.etlsetup.dto.request;

import dev.zendal.etlsetup.dto.datasource.DataSourceSettings;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EtlSessionChooseOutput {

    @NotNull
    private DataSourceSettings dataSourceSettings;

}
