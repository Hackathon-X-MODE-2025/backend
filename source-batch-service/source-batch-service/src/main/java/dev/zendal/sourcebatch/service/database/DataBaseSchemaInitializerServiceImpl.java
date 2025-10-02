package dev.zendal.sourcebatch.service.database;

import dev.zendal.sourcebatch.dto.request.DataBaseInitRequest;
import dev.zendal.sourcebatch.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DataBaseSchemaInitializerServiceImpl implements DataBaseSchemaInitializerService {

    private final List<DataSourceInitializerHandler> handlers;

    @Override
    public void initSchema(DataBaseInitRequest settings) {
        this.handlers.stream()
                .filter(handler -> handler.supportedDataSourceSettings().equals(settings.getDataSourceSettings().type()))
                .findFirst().orElseThrow(
                        () -> new EntityNotFoundException("Can't find handler for " + settings.getDataSourceSettings().type())
                ).initSchema(settings);
    }
}
