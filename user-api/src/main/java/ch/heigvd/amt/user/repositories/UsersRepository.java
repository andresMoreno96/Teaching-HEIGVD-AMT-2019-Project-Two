package ch.heigvd.amt.user.repositories;

import ch.heigvd.amt.user.entities.UserEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UsersRepository extends CrudRepository<UserEntity, String> {

    List<UserEntity> findByEmailAndPassword(String email, String password);
}
