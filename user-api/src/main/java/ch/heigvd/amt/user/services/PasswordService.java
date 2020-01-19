package ch.heigvd.amt.user.services;

import ch.heigvd.amt.user.entities.PasswordResetEntity;
import ch.heigvd.amt.user.entities.UserEntity;
import ch.heigvd.amt.user.repositories.PasswordResetsRepository;
import ch.heigvd.amt.user.repositories.UsersRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PasswordService {

    @Autowired
    private PasswordResetsRepository passwordResetsRepository;

    @Autowired
    private UsersRepository usersRepository;

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

    public PasswordResetEntity createResetRequest(UserEntity user) {
        PasswordResetEntity entity = new PasswordResetEntity(user);
        passwordResetsRepository.save(entity);
        return entity;
    }

    public boolean resetPassword(String email, String uuid, String password) {
        PasswordResetEntity reset = passwordResetsRepository.findByUserEmailAndUuid(email, uuid);

        if (reset == null) {
            return false;
        }

        reset.getUser().setPassword(hashPassword(password));
        usersRepository.save(reset.getUser());
        passwordResetsRepository.delete(reset);

        return true;
    }
}
