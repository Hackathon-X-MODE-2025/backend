package dev.zendal.etlsetup.dto.response;


import lombok.Builder;

import java.util.UUID;

@Builder
public record UserDto(
        UUID id,
        String login
) {
}
