package ch.heigvd.amt.user.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DataGenerator implements ApplicationRunner {

    private boolean generateData;

    public DataGenerator(@Value("${app.data.generate}") boolean generateData) {
        this.generateData = generateData;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (generateData) {

        }
    }
}
