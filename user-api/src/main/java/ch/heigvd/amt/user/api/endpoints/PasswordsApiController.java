package ch.heigvd.amt.user.api.endpoints;

import ch.heigvd.amt.user.api.PasswordsApi;
import ch.heigvd.amt.user.repositories.PasswordResetsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

@Controller
public class PasswordsApiController implements PasswordsApi {

    @Autowired
    PasswordResetsRepository passwordResetsRepository;

    @Override
    public ResponseEntity<Void> changePassword(String password) {
        return null;
    }
}
