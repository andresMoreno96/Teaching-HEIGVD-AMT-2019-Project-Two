package ch.heigvd.amt.user.repositories;

import ch.heigvd.amt.user.entities.PasswordResetEntity;
import ch.heigvd.amt.user.entities.UserEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PasswordResetsRepository extends CrudRepository<PasswordResetEntity, UserEntity> {
    PasswordResetEntity findByUserEmailAndUuid(String email, String uuid);
}
