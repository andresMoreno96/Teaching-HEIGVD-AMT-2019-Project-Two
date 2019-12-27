package ch.heigvd.amt.user.api.endpoints;

import ch.heigvd.amt.user.api.UsersApi;
import ch.heigvd.amt.user.api.model.User;
import ch.heigvd.amt.user.api.model.UserNoPassword;
import ch.heigvd.amt.user.entities.UserEntity;
import ch.heigvd.amt.user.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import javax.validation.Valid;
import java.util.Optional;

@Controller
public class UsersApiController implements UsersApi {

    @Autowired
    private UsersRepository usersRepository;

    @Override
    public ResponseEntity<Void> createUser(@Valid User user) {
        UserEntity entity = new UserEntity(user);
        usersRepository.save(entity);

        return ResponseEntity.status(200).build();
    }

    @Override
    public ResponseEntity<UserNoPassword> getUser(String email) {

        Optional<UserEntity> entity = usersRepository.findById(email);
        if (entity.isPresent()) {
            return ResponseEntity.status(200).body(entity.get().toUser());
        }

        return ResponseEntity.status(404).build();
    }
}
