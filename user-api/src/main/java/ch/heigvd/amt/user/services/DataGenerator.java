package ch.heigvd.amt.user.services;


import ch.heigvd.amt.user.entities.PasswordResetEntity;
import ch.heigvd.amt.user.entities.UserEntity;
import ch.heigvd.amt.user.repositories.UsersRepository;
import ch.heigvd.amt.user.api.model.User;
import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.logging.Logger;

@Component
public class DataGenerator implements ApplicationRunner {

    private static final Logger LOG = Logger.getLogger(DataGenerator.class.getName());
    private static final int SAVE_BATCH_COUNT = 1000;

    private boolean generateData;
    private int generateCount;
    private UsersRepository usersRepository;
    private PasswordService passwordService;

    public DataGenerator(@Value("${app.data.generate}") boolean generateData,
                         @Value("${app.data.generate.count}") int generateCount,
                         UsersRepository usersRepository, PasswordService passwordService) {
        this.generateData = generateData;
        this.generateCount = generateCount;
        this.usersRepository = usersRepository;
        this.passwordService = passwordService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (generateData) {
            Faker faker = new Faker();
            String password = passwordService.hashPassword("123");

            LOG.info("Generating users ...");

            LinkedList<UserEntity> users = new LinkedList<>();
            for (int i = 1; i <= generateCount; ++i) {
                users.add(createUser(faker, i, password));

                if (i % SAVE_BATCH_COUNT == 0) {
                    usersRepository.saveAll(users);
                    LOG.info(String.format("Saved %d users.", i));
                    users.clear();
                }
            }

            if (!users.isEmpty()) {
                usersRepository.saveAll(users);
                LOG.info(String.format("Saved %d users.", generateCount));
            }

            LOG.info("Data generation done!");
        }
    }

    private UserEntity createUser(Faker faker, int i, String password) {
        String firsname = faker.name().firstName();
        String lastName = faker.name().lastName();

        return new UserEntity(new User()
                .email(i + "@pot.ato")
                .firstName(firsname)
                .lastName(lastName)
                .password(password));
    }
}
