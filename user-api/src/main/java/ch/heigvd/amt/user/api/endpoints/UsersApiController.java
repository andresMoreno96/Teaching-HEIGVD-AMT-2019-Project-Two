package ch.heigvd.amt.user.api.endpoints;

import ch.heigvd.amt.user.api.UsersApi;
import ch.heigvd.amt.user.api.model.User;
import ch.heigvd.amt.user.api.model.UserNoPassword;
import ch.heigvd.amt.user.api.util.JwtManager;
import ch.heigvd.amt.user.entities.PasswordResetEntity;
import ch.heigvd.amt.user.entities.UserEntity;
import ch.heigvd.amt.user.repositories.PasswordResetsRepository;
import ch.heigvd.amt.user.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.validation.*;
import java.util.Optional;
import java.util.Set;

@Controller
public class UsersApiController implements UsersApi {

    @Resource
    Validator validator;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private PasswordResetsRepository passwordResetsRepository;

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
        if (!entity.isPresent()) {
            return ResponseEntity.status(404).build();
        }

        return ResponseEntity.status(200).body(entity.get().toUser());
    }

    @Override
    public ResponseEntity<Void> resetPassword(String email) {

        Optional<UserEntity> user = usersRepository.findById(email);

        if (!user.isPresent()) {
            return ResponseEntity.status(404).build();
        }

        PasswordResetEntity pwdReset = new PasswordResetEntity(user.get());
        passwordResetsRepository.save(pwdReset);

        String token = new JwtManager("secret").passwordResetToken(
                String.valueOf(pwdReset.getId()),
                pwdReset.getUser().getEmail(),
                pwdReset.getExpireOn()
        );

        // TODO: send email
        System.out.println(token);

        return ResponseEntity.status(200).build();
    }
}
