package ch.heigvd.amt.user.api.endpoints;


import ch.heigvd.amt.user.api.util.JwtFilter;
import ch.heigvd.amt.user.entities.PasswordResetEntity;
import ch.heigvd.amt.user.repositories.PasswordResetsRepository;
import ch.heigvd.amt.user.repositories.UsersRepository;
import ch.heigvd.amt.user.services.PasswordService;
import ch.heigvd.amt.userEmail.api.PasswordsApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;

@Controller
public class PasswordsApiController implements PasswordsApi {

    @Autowired
    private PasswordResetsRepository passwordResetsRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private PasswordService passwordService;

    @Override
    public ResponseEntity<Void> changePassword(String password) {

        String email = (String) request.getAttribute(JwtFilter.EMAIL_REQUEST_ATTRIBUTE);
        Long id = (Long) request.getAttribute(JwtFilter.PWD_RESET_REQUEST_ATTRIBUTE);

        if (email != null && id != null) {

            PasswordResetEntity reset = passwordResetsRepository.findByIdAndUserEmail(id, email);

            if (reset != null) {

                reset.getUser().setPassword(passwordService.hashPassword(password));
                usersRepository.save(reset.getUser());
                passwordResetsRepository.delete(reset);

                return ResponseEntity.status(200).build();
            }
        }

        return ResponseEntity.status(401).build();
    }
}
