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
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.validation.*;
import java.util.Optional;
import java.util.Set;

@Controller
public class UsersApiController implements UsersApi {


    private UsersRepository usersRepository;
    private PasswordResetsRepository passwordResetsRepository;
    private JwtManager jwtManager;
    private JavaMailSender mailSender;
    private Validator validator;

    public UsersApiController(UsersRepository usersRepository, PasswordResetsRepository passwordResetsRepository,
                              JwtManager jwtManager, JavaMailSender mailSender, Validator validator) {
        this.usersRepository = usersRepository;
        this.passwordResetsRepository = passwordResetsRepository;
        this.jwtManager = jwtManager;
        this.mailSender = mailSender;
        this.validator = validator;
    }

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

        String token = jwtManager.createToken(
                pwdReset.getUser().getEmail(), pwdReset.getId()
        );

        // Send email
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(user.get().getEmail());
        mail.setSubject("Password Reset Token");
        mail.setText(token);
        mailSender.send(mail);

        return ResponseEntity.status(200).build();
    }
}
