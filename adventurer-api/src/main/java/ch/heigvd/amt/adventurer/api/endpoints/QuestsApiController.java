package ch.heigvd.amt.adventurer.api.endpoints;

import ch.heigvd.amt.adventurer.api.QuestsApi;
import ch.heigvd.amt.adventurer.api.model.Adventurer;
import ch.heigvd.amt.adventurer.api.model.Quest;
import ch.heigvd.amt.adventurer.api.model.QuestCreate;
import ch.heigvd.amt.adventurer.api.model.QuestUpdate;
import ch.heigvd.amt.adventurer.api.util.JwtFilterAdv;
import ch.heigvd.amt.adventurer.entities.AdventurerEntity;
import ch.heigvd.amt.adventurer.entities.QuestEntity;
import ch.heigvd.amt.adventurer.repositories.AdventurerRepository;
import ch.heigvd.amt.adventurer.repositories.QuestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.request.NativeWebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
public class QuestsApiController implements QuestsApi {

    @Autowired
    private QuestRepository questRepository;

    @Autowired
    private AdventurerRepository adventurerRepository;

    @Autowired
    private HttpServletRequest request;


    @Override
    public ResponseEntity<Adventurer> createQuest(@Valid QuestCreate quest) {

        if (!adventurerRepository.existsById(quest.getAdventurerName())) {
            return ResponseEntity.status(400).build();
        }

        AdventurerEntity adventurerEntity = adventurerRepository.findById(quest.getAdventurerName()).get();

        String tokenEmail = (String) request.getAttribute(JwtFilterAdv.EMAIL_REQUEST_ATTRIBUTE);
        if (tokenEmail == null || !tokenEmail.equals(adventurerEntity.getUserEmail())) {
            return ResponseEntity.status(401).build();
        }

        QuestEntity questEntity = new QuestEntity(quest, adventurerEntity);

        questRepository.save(questEntity);


        return ResponseEntity.status(200).build();


    }

    @Override
    public ResponseEntity<Void> deleteQuest(Integer id) {

        if (!questRepository.existsById(id.toString())) {

            return ResponseEntity.status(400).build();
        }

        QuestEntity questEntity = questRepository.findById(id.toString()).get();

        String tokenEmail = (String) request.getAttribute(JwtFilterAdv.EMAIL_REQUEST_ATTRIBUTE);
        if (tokenEmail == null || !tokenEmail.equals(questEntity.getOwner().getUserEmail())) {
            return ResponseEntity.status(401).build();
        }

        questRepository.deleteById(id.toString());


        return ResponseEntity.status(200).build();
    }

    @Override
    public ResponseEntity<Quest> endQuest(Integer id) {
        return null;
    }

    @Override
    public ResponseEntity<Quest> getQuest(Integer id) {
        return null;
    }

    @Override
    public ResponseEntity<List<Quest>> getQuests(@Valid String limit, @Valid String offset) {
        return null;
    }

    @Override
    public ResponseEntity<Quest> updateQuest(Integer id, @Valid QuestUpdate informations) {
        return null;
    }
}
