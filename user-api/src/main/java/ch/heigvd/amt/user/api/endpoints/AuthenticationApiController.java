package ch.heigvd.amt.user.api.endpoints;

import ch.heigvd.amt.user.api.AuthenticationApi;
import ch.heigvd.amt.user.api.util.JwtManager;
import ch.heigvd.amt.user.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import sun.tools.jconsole.JConsole;

@Controller
public class AuthenticationApiController implements AuthenticationApi {

    @Autowired
    UsersRepository usersRepository;

    @Override
    public ResponseEntity<String> authenticateUser(String email, String password) {

        if (usersRepository.findByEmailAndPassword(email, password).size() == 1) {

            String token = new JwtManager("potato").createToken(email);
            return ResponseEntity.status(200).body(token);
        }

        return ResponseEntity.status(400).build();
    }
}
