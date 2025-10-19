package dev.zendal.etlsetup.service.session;

import dev.zendal.etlsetup.domain.EtlSessionStatus;
import dev.zendal.etlsetup.domain.source.EtlSessionSourceEntity;
import dev.zendal.etlsetup.domain.source.EtlSessionSourceStatus;
import dev.zendal.etlsetup.dto.request.EtlSessionCreationRequest;
import dev.zendal.etlsetup.dto.response.EtlSessionDto;
import dev.zendal.etlsetup.mapper.EtlSessionMapper;
import dev.zendal.etlsetup.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EtlSessionCreationServiceImpl implements EtlSessionCreationService {

    private final EtlSessionService etlSessionService;

    private final EtlSessionMapper etlSessionMapper;

    private final UserService userService;

    @Override
    @Transactional
    public EtlSessionDto create(EtlSessionCreationRequest request) {

        final var session = this.etlSessionMapper.to(request)
                .setId(UUID.randomUUID())
                .setUser(this.userService.getUserEntity(request.getUserId()))
                .setStatus(EtlSessionStatus.ANALYZING);

        session.addAll(
                request.getSourceSettings().stream()
                        .map(val ->
                                new EtlSessionSourceEntity()
                                        .setStatus(EtlSessionSourceStatus.PREPARING)
                                        .setType(val.type())
                                        .setContext(val)
                        )
                        .toList()
        );

        return this.etlSessionService.create(session);
    }
}
