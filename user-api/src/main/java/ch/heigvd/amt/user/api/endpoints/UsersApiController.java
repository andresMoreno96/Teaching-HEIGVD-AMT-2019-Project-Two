package ch.heigvd.amt.user.api.endpoints;

import ch.heigvd.amt.user.api.UsersApi;
import ch.heigvd.amt.user.api.model.User;
import ch.heigvd.amt.user.api.model.UserNoPassword;
import ch.heigvd.amt.user.entities.UserEntity;
import ch.heigvd.amt.user.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.annotation.Resource;
import javax.persistence.RollbackException;
import javax.validation.*;
import javax.xml.crypto.Data;
import java.util.Optional;
import java.util.Set;

@Controller
public class UsersApiController implements UsersApi {

    @Resource
    Validator validator;

    @Autowired
    private UsersRepository usersRepository;

    @Override
    public ResponseEntity<Void> createUser(User user) {

        UserEntity entity = new UserEntity(user);

        Set<ConstraintViolation<UserEntity>> violations = validator.validate(entity);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }

        if (!usersRepository.existsById(entity.getEmail())) {
            usersRepository.save(entity);
            return ResponseEntity.status(200).build();
        }

        return ResponseEntity.status(400).build();
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
