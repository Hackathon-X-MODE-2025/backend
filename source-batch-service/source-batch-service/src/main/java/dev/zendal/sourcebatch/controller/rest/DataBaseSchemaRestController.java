package dev.zendal.sourcebatch.controller.rest;

import dev.zendal.sourcebatch.dto.request.DataBaseInitRequest;
import dev.zendal.sourcebatch.service.database.DataBaseSchemaInitializerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.zendal.backend.configuration.WebConstants;

@RestController
@RequestMapping(WebConstants.FULL_PRIVATE + "/databases")
@RequiredArgsConstructor
public class DataBaseSchemaRestController {

    private final DataBaseSchemaInitializerService dataBaseSchemaInitializerService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody DataBaseInitRequest settings) {
        this.dataBaseSchemaInitializerService.initSchema(settings);
    }
}
