package ch.heigvd.amt.user.api.endpoints;

import ch.heigvd.amt.user.api.AuthenticationApi;
import ch.heigvd.amt.user.api.util.JwtManager;
import ch.heigvd.amt.user.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

@Controller
public class AuthenticationApiController implements AuthenticationApi {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private JwtManager jwtManager;

    @Override
    public ResponseEntity<String> authenticateUser(String email, String password) {

        if (usersRepository.findByEmailAndPassword(email, password).size() == 1) {

            String token = jwtManager.createToken(email);
            return ResponseEntity.status(200).body(token);
        }

        return ResponseEntity.status(400).build();
    }
}
