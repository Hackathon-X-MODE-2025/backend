package dev.zendal.etlsetup.service.user;

import dev.zendal.etlsetup.domain.UserEntity;
import dev.zendal.etlsetup.dto.response.UserDto;

import java.util.UUID;

public interface UserService {

    void createUser(String login, String password);

    UserDto getUserDto(String login, String password);

    UserEntity getUserEntity(UUID id);
}
