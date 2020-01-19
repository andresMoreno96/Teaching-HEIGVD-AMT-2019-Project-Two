package ch.heigvd.amt.adventurer.api.endpoints;

import ch.heigvd.amt.adventurer.api.ParticipationApi;
import ch.heigvd.amt.adventurer.api.util.JwtFilterAdv;
import ch.heigvd.amt.adventurer.entities.AdventurerEntity;
import ch.heigvd.amt.adventurer.entities.QuestEntity;
import ch.heigvd.amt.adventurer.repositories.AdventurerRepository;
import ch.heigvd.amt.adventurer.repositories.QuestRepository;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.NativeWebRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Controller
public class ParticipationApiController implements ParticipationApi {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private AdventurerRepository adventurerRepository;

    @Autowired
    private QuestRepository questRepository;

    @Override
    public ResponseEntity<Void> joinQuest(String adventurerName, Integer questId) {

        Optional<AdventurerEntity> adventurer = adventurerRepository.findById(adventurerName);
        Optional<QuestEntity> quest = questRepository.findById((long) questId);

        if (!adventurer.isPresent() || !quest.isPresent()) {
            return ResponseEntity.status(404).build();
        }

        if (quest.get().isEnded()) {
            return ResponseEntity.status(405).build();
        }

        String tokenEmail = (String) request.getAttribute(JwtFilterAdv.EMAIL_REQUEST_ATTRIBUTE);
        if (tokenEmail == null || !tokenEmail.equals(adventurer.get().getUserEmail())) {
            return ResponseEntity.status(401).build();
        }

        adventurer.get().getParticipation().add(quest.get());
        adventurerRepository.save(adventurer.get());

        return ResponseEntity.status(200).build();
    }

    @Override
    @RequestMapping(value = "/participation", method = RequestMethod.DELETE)
    public ResponseEntity<Void> quitQuest(String adventurerName, Integer questId) {
        Optional<AdventurerEntity> adventurer = adventurerRepository.findById(adventurerName);
        Optional<QuestEntity> quest = questRepository.findById((long) questId);

        if (!adventurer.isPresent() || !quest.isPresent()) {
            return ResponseEntity.status(404).build();
        }

        if (quest.get().isEnded()) {
            return ResponseEntity.status(405).build();
        }

        String tokenEmail = (String) request.getAttribute(JwtFilterAdv.EMAIL_REQUEST_ATTRIBUTE);
        if (tokenEmail == null || !tokenEmail.equals(adventurer.get().getUserEmail())) {
            return ResponseEntity.status(401).build();
        }

        adventurer.get().getParticipation().remove(quest.get());
        adventurerRepository.save(adventurer.get());

        return ResponseEntity.status(200).build();
    }
}
