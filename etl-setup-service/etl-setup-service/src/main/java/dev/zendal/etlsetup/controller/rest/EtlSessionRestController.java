package dev.zendal.etlsetup.controller.rest;

import dev.zendal.etlsetup.dto.request.EtlSessionChooseOutput;
import dev.zendal.etlsetup.dto.request.EtlSessionCreationRequest;
import dev.zendal.etlsetup.dto.response.EtlSessionDto;
import dev.zendal.etlsetup.dto.response.EtlSessionShortDto;
import dev.zendal.etlsetup.service.session.EtlSessionCreationService;
import dev.zendal.etlsetup.service.session.EtlSessionFindService;
import dev.zendal.etlsetup.service.session.EtlSessionUserSelectDataSourceService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.web.bind.annotation.*;
import org.zendal.backend.configuration.WebConstants;

import java.util.UUID;

@RestController
@RequestMapping({
        WebConstants.FULL_PRIVATE + "/sessions",
        WebConstants.FULL_WEB + "/sessions"
})
@RequiredArgsConstructor
public class EtlSessionRestController {

    private final EtlSessionCreationService etlSessionCreationService;

    private final EtlSessionFindService etlSessionFindService;

    private final EtlSessionUserSelectDataSourceService etlSessionUserSelectDataSourceService;


    @Operation(
            description = "Если CSV - CsvHDFSSourceSettings, Json - JsonHDFSSourceSettings, XML - XmlHDFSSourceSettings, PostgreSQL - PostgreSQLSourceSettings"
    )
    @PostMapping
    public EtlSessionDto create(@Valid @RequestBody EtlSessionCreationRequest request) {
        return this.etlSessionCreationService.create(request);
    }

    @GetMapping
    public PagedModel<EtlSessionShortDto> get(Pageable pageable) {
        return this.etlSessionFindService.findAll(pageable);
    }

    @GetMapping("/{id}")
    public EtlSessionDto getById(@PathVariable UUID id) {
        return this.etlSessionFindService.findById(id);
    }

    @Operation(
            description = "HDFSDataSourceSettings, ClickHouseDataSourceSettings, PostgreSQLDataSourceSettings"
    )
    @PostMapping("/{id}/choose-data-base")
    public void choose(@PathVariable UUID id, @Valid @RequestBody EtlSessionChooseOutput request) {
        this.etlSessionUserSelectDataSourceService.selectDataSource(id, request.getDataSourceSettings());
    }
}
