package ch.heigvd.amt.adventurer.api.endpoints;

import ch.heigvd.amt.adventurer.api.AdventurersApi;
import ch.heigvd.amt.adventurer.api.model.Adventurer;
import ch.heigvd.amt.adventurer.api.model.AdventurerUpdate;
import ch.heigvd.amt.adventurer.api.util.JwtFilterAdv;
import ch.heigvd.amt.adventurer.entities.AdventurerEntity;
import ch.heigvd.amt.adventurer.repositories.AdventurerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.Validator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class AdventurersApiController implements AdventurersApi {


    @Autowired
    private AdventurerRepository adventurerRepository;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private Validator validator;


    @Override
    public ResponseEntity<Adventurer> createAdventurer(@Valid Adventurer adventurer) {

        String tokenEmail = (String) request.getAttribute(JwtFilterAdv.EMAIL_REQUEST_ATTRIBUTE);
        if (tokenEmail == null) {
            return ResponseEntity.status(401).build();
        }

        AdventurerEntity adventurerEntity = new AdventurerEntity(adventurer, tokenEmail);

        Set<ConstraintViolation<AdventurerEntity>> violations = validator.validate(adventurerEntity);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }

        if (!adventurerRepository.existsById(adventurerEntity.getName())) {
            adventurerRepository.save(adventurerEntity);
            return ResponseEntity.status(200).body(adventurerEntity.toAdventurer());
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
        return adventurerRepository.findById(name)
                .map(adventurerEntity -> ResponseEntity.status(200).body(adventurerEntity.toAdventurer()))
                .orElseGet(() -> ResponseEntity.status(404).build());
    }

    @Override
    public ResponseEntity<List<Adventurer>> getAdventurers(@Valid String limitValue, @Valid String pageValue) {

        Integer limit = toNumber(limitValue);
        Integer page = toNumber(pageValue);

        final LinkedList<Adventurer> adventurers = new LinkedList<>();
        if (limit != null) {
            adventurerRepository.findAll(PageRequest.of(page != null ? page : 0, limit))
                    .forEach(entity -> adventurers.add(entity.toAdventurer()));
        } else {
            adventurerRepository.findAll()
                    .forEach(entity -> adventurers.add(entity.toAdventurer()));
        }

        return ResponseEntity.ok(adventurers);
    }

    private Integer toNumber(String value) {
        if (value == null) {
            return null;
        }

        try {
            return Integer.valueOf(value);
        } catch (NumberFormatException e) {
            return null;
        }
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
