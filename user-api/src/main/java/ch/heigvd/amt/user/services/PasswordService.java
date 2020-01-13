package ch.heigvd.amt.user.services;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Component;

@Component
public class PasswordService {

    public String hashPassword(String plainTextPassword) {
        return BCrypt.hashpw(plainTextPassword, BCrypt.gensalt());
    }

    public boolean checkPassword(String plainTextPassword, String hashedPassword) {
        try {
            boolean correct = BCrypt.checkpw(plainTextPassword, hashedPassword);
            return correct;
        } catch (Exception e) {
            return false;
        }
    }
}
