package dev.zendal.etlsetup.repository;

import dev.zendal.etlsetup.domain.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<UserEntity, UUID> {

    Optional<UserEntity> findByLoginAndPassword(String login, String password);
}
