package ch.heigvd.amt.user.api.endpoints;


import ch.heigvd.amt.user.api.util.JwtFilter;
import ch.heigvd.amt.user.services.EmailService;
import ch.heigvd.amt.user.services.JwtManager;
import ch.heigvd.amt.user.entities.PasswordResetEntity;
import ch.heigvd.amt.user.entities.UserEntity;
import ch.heigvd.amt.user.repositories.PasswordResetsRepository;
import ch.heigvd.amt.user.repositories.UsersRepository;
import ch.heigvd.amt.user.services.PasswordService;
import ch.heigvd.amt.userEmail.api.UsersApi;
import ch.heigvd.amt.userEmail.api.model.User;
import ch.heigvd.amt.userEmail.api.model.UserNoPassword;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.*;
import java.util.Optional;
import java.util.Set;

@Controller
public class UsersApiController implements UsersApi {


    private UsersRepository usersRepository;
    private PasswordResetsRepository passwordResetsRepository;
    private PasswordService passwordService;
    private JwtManager jwtManager;
    private EmailService emailService;
    private Validator validator;
    private HttpServletRequest request;

    public UsersApiController(UsersRepository usersRepository, PasswordResetsRepository passwordResetsRepository,
                              PasswordService passwordService, JwtManager jwtManager, EmailService emailService,
                              Validator validator, HttpServletRequest request) {
        this.usersRepository = usersRepository;
        this.passwordResetsRepository = passwordResetsRepository;
        this.passwordService = passwordService;
        this.jwtManager = jwtManager;
        this.emailService = emailService;
        this.validator = validator;
        this.request = request;
    }

    @Override
    public ResponseEntity<Void> createUser(User user) {

        UserEntity entity = new UserEntity(user);

        Set<ConstraintViolation<UserEntity>> violations = validator.validate(entity);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }

        // hash user password;
        entity.setPassword(passwordService.hashPassword(entity.getPassword()));

        if (!usersRepository.existsById(entity.getEmail())) {
            usersRepository.save(entity);
            return ResponseEntity.status(200).build();
        }

        return ResponseEntity.status(400).build();
    }

    @Override
    public ResponseEntity<UserNoPassword> getUser(String email) {

        String tokenEmail = (String) request.getAttribute(JwtFilter.EMAIL_REQUEST_ATTRIBUTE);
        if (tokenEmail == null || !tokenEmail.equals(email)) {
            return ResponseEntity.status(401).build();
        }

        return usersRepository.findById(email)
                .map(userEntity -> ResponseEntity.status(200).body(userEntity.toUser()))
                .orElseGet(() -> ResponseEntity.status(404).build());
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

        //Send email
        emailService.sendEmail(user.get().getEmail(), "Password Reset Token", token);

        return ResponseEntity.status(200).build();
    }
}
