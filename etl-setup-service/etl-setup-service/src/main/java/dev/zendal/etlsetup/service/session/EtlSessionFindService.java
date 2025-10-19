package dev.zendal.etlsetup.service.session;

import dev.zendal.etlsetup.dto.response.EtlSessionDto;
import dev.zendal.etlsetup.dto.response.EtlSessionShortDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;

import java.util.UUID;

public interface EtlSessionFindService {


    PagedModel<EtlSessionShortDto> findAll(UUID userId, Pageable pageable);


    EtlSessionDto findById(UUID userId, UUID id);
}
