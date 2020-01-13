package ch.heigvd.amt.user.api.endpoints;

import ch.heigvd.amt.user.api.AuthenticationApi;
import ch.heigvd.amt.user.entities.UserEntity;
import ch.heigvd.amt.user.services.JwtManager;
import ch.heigvd.amt.user.repositories.UsersRepository;
import ch.heigvd.amt.user.services.PasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.util.Optional;

@Controller
public class AuthenticationApiController implements AuthenticationApi {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private JwtManager jwtManager;

    @Autowired
    private PasswordService passwordService;

    @Override
    public ResponseEntity<String> authenticateUser(String email, String password) {

        Optional<UserEntity> user = usersRepository.findById(email);
        if (user.isPresent() && passwordService.checkPassword(password, user.get().getPassword())) {
            String token = jwtManager.createToken(email);
            return ResponseEntity.status(200).body(token);
        }

        return ResponseEntity.status(400).build();
    }
}
