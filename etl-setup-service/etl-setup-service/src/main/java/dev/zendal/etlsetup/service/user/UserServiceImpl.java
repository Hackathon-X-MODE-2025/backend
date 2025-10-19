package dev.zendal.etlsetup.service.user;

import dev.zendal.etlsetup.domain.UserEntity;
import dev.zendal.etlsetup.dto.response.UserDto;
import dev.zendal.etlsetup.exception.EntityNotFoundException;
import dev.zendal.etlsetup.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public void createUser(String login, String password) {
        this.userRepository.saveAndFlush(
                new UserEntity()
                        .setId(UUID.randomUUID())
                        .setLogin(this.mapLogin(login))
                        .setPassword(password)
        );
    }

    @Override
    public UserDto getUserDto(String login, String password) {
        return this.userRepository.findByLoginAndPassword(
                        this.mapLogin(login),
                        password
                )
                .map(v ->
                        UserDto.builder()
                                .id(v.getId())
                                .login(v.getLogin())
                                .build()
                )
                .orElseThrow(
                        () -> new EntityNotFoundException("Can't find user: " + login)
                );
    }

    @Override
    @Transactional(readOnly = true)
    public UserEntity getUserEntity(UUID id) {
        return this.userRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Can't find user with id: " + id)
        );
    }

    private String mapLogin(String login) {
        return login.toLowerCase().strip();
    }
}
