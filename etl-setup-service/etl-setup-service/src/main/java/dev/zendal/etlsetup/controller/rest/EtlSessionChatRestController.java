package dev.zendal.etlsetup.controller.rest;

import dev.zendal.etlsetup.dto.chat.EtlSessionChatDto;
import dev.zendal.etlsetup.dto.request.EtlSessionChatPatchRequest;
import dev.zendal.etlsetup.service.chat.EtlSessionChatCommitService;
import dev.zendal.etlsetup.service.chat.EtlSessionChatFindService;
import dev.zendal.etlsetup.service.chat.EtlSessionPatchService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.web.bind.annotation.*;
import org.zendal.backend.configuration.WebConstants;

import java.util.UUID;

@RestController
@RequestMapping({
        WebConstants.FULL_PRIVATE + "/sessions/{sessionId}/chats",
        WebConstants.FULL_WEB + "/sessions/{sessionId}/chats"
})
@RequiredArgsConstructor
public class EtlSessionChatRestController {

    private final EtlSessionChatFindService etlSessionChatFindService;

    private final EtlSessionChatCommitService etlSessionChatCommitService;

    private final EtlSessionPatchService etlSessionPatchService;

    @GetMapping
    public PagedModel<EtlSessionChatDto> getAll(@PathVariable UUID sessionId, Pageable pageable) {
        return this.etlSessionChatFindService.findBySessionId(sessionId, pageable);
    }

    @Operation(description = "Согласовать чат")
    @PostMapping("/commit")
    public void flush(@PathVariable UUID sessionId) {
        this.etlSessionChatCommitService.commit(sessionId);
    }

    @PatchMapping
    public void patch(@PathVariable UUID sessionId, @RequestBody EtlSessionChatPatchRequest dto) {
        this.etlSessionPatchService.edit(sessionId, dto);
    }

}
