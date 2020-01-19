package ch.heigvd.amt.adventurer.api.endpoints;

import ch.heigvd.amt.adventurer.api.AdventurersApi;
import ch.heigvd.amt.adventurer.api.model.Adventurer;
import ch.heigvd.amt.adventurer.api.model.AdventurerUpdate;
import ch.heigvd.amt.adventurer.api.util.JwtFilterAdv;
import ch.heigvd.amt.adventurer.entities.AdventurerEntity;
import ch.heigvd.amt.adventurer.repositories.AdventurerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@Controller
public class AdventurersApiController implements AdventurersApi {


    @Autowired
    private AdventurerRepository adventurerRepository;

    @Autowired
    private HttpServletRequest request;


    @Override
    public ResponseEntity<Adventurer> createAdventurer(@Valid Adventurer adventurer) {

        String tokenEmail = (String) request.getAttribute(JwtFilterAdv.EMAIL_REQUEST_ATTRIBUTE);
        if (tokenEmail == null) {
            return ResponseEntity.status(406).build();
        }

        AdventurerEntity adventurerEntity = new AdventurerEntity(adventurer, tokenEmail);
        if (!adventurerRepository.existsById(adventurerEntity.getName())) {
            adventurerRepository.save(adventurerEntity);
            return ResponseEntity.status(200).build();
        }

        return ResponseEntity.status(400).build();
    }

    @Override
    public ResponseEntity<Void> deleteAdventurer(String name) {

        if (!adventurerRepository.existsById(name)) {

            return ResponseEntity.status(400).build();
        }

        if (validatesTheRightUser(name)){

            return ResponseEntity.status(401).build();
        }

        adventurerRepository.deleteById(name);

        return ResponseEntity.status(200).build();

    }


    @Override
    public ResponseEntity<Adventurer> getAdventurer(String name) {

        if (!adventurerRepository.existsById(name)) {

            return ResponseEntity.status(400).build();
        }

        if (validatesTheRightUser(name)){

            return ResponseEntity.status(401).build();
        }

        return adventurerRepository.findById(name).map(adventurerEntity -> ResponseEntity.status(200).body(adventurerEntity.toAdventurer()))
                .orElseGet(() -> ResponseEntity.status(404).build());
    }

    @Override
    public ResponseEntity<List<Adventurer>> getAdventurers(@Valid String limit, @Valid String offset) {
        return null;
    }

    @Override
    public ResponseEntity<Adventurer> updateAdventurer(String name, @Valid AdventurerUpdate informations) {

        if (!adventurerRepository.existsById(name)) {

            return ResponseEntity.status(400).build();
        }

        AdventurerEntity adventurerEntity=adventurerRepository.findById(name).get();

        adventurerEntity.setJob(informations.getJob());

        adventurerRepository.save(adventurerEntity);

        return ResponseEntity.status(200).build();


    }


    private boolean validatesTheRightUser(String name) {
        String tokenEmail = (String) request.getAttribute(JwtFilterAdv.EMAIL_REQUEST_ATTRIBUTE);
        if (tokenEmail == null || !tokenEmail.equals(adventurerRepository.findById(name).get().getUserEmail())) {

            return true;
        }
        return false;
    }
}
