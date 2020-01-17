package ch.heigvd.amt.adventurer.api.endpoints;

import ch.heigvd.amt.adventurer.api.AdventurersApi;
import ch.heigvd.amt.adventurer.api.model.Adventurer;
import ch.heigvd.amt.adventurer.api.model.AdventurerUpdate;
import ch.heigvd.amt.adventurer.entities.AdventurerEntity;
import ch.heigvd.amt.adventurer.repositories.AdventurerRepository;
import ch.heigvd.amt.adventurer.repositories.QuestRepository;
import ch.heigvd.amt.adventurer.services.JwtManagerAdv;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.context.request.NativeWebRequest;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
public class AdventurersApiController implements AdventurersApi {


    @Autowired
    private AdventurerRepository adventurerRepository;

    @Autowired
    private JwtManagerAdv jwtManager;

    @Autowired
    private QuestRepository questRepository;


    @Override
    public ResponseEntity<Adventurer> createAdventurer(@Valid Adventurer adventurer) {

        AdventurerEntity adventurerEntity=new AdventurerEntity(adventurer);



        return null;
    }

    @Override
    public ResponseEntity<Void> deleteAdventurer(String name) {
        return null;
    }

    @Override
    public ResponseEntity<Adventurer> getAdventurer(String name) {
        return null;
    }

    @Override
    public ResponseEntity<List<Adventurer>> getAdventurers(@Valid String limit, @Valid String offset) {
        return null;
    }

    @Override
    public ResponseEntity<Adventurer> updateAdventurer(String name, @Valid AdventurerUpdate informations) {
        return null;
    }
}
