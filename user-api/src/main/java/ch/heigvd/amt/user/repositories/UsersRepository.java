package ch.heigvd.amt.user.repositories;

import ch.heigvd.amt.user.entities.UserEntity;
import org.springframework.data.repository.CrudRepository;

public interface UsersRepository extends CrudRepository<UserEntity, String> {}
