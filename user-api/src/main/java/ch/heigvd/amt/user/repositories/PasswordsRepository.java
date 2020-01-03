package ch.heigvd.amt.user.repositories;

import ch.heigvd.amt.user.entities.PasswordResetEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PasswordsRepository extends CrudRepository<PasswordResetEntity, Long> {

    List<PasswordResetEntity> findByIdAndUserOrderByExpireOnDesc(Long id, String email);
}
