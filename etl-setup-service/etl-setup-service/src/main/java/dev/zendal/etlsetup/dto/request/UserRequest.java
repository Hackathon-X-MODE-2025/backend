package dev.zendal.etlsetup.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class UserRequest {

    @NotEmpty
    private String login;

    @NotEmpty
    private String password;
}
