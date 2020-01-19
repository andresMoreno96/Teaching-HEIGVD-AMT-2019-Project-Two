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
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.request.NativeWebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.LinkedList;
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
    public ResponseEntity<Quest> createQuest(@Valid QuestCreate quest) {

        if (!adventurerRepository.existsById(quest.getAdventurerName())) {
            return ResponseEntity.status(400).build();
        }

        AdventurerEntity adventurerEntity = adventurerRepository.findById(quest.getAdventurerName()).get();

        if (validateAccount(adventurerEntity)) return ResponseEntity.status(401).build();

        QuestEntity questEntity = new QuestEntity(quest, adventurerEntity);

        questRepository.save(questEntity);


        return ResponseEntity.status(200).body(questEntity.toQuest());


    }

    @Override
    public ResponseEntity<Void> deleteQuest(Integer id) {

        QuestEntity questEntity = checkIfExistAndReturnQuest(id);
        if (questEntity == null) {
            return ResponseEntity.status(400).build();
        }

        if (validateAccount(questEntity.getOwner())) {
            return ResponseEntity.status(401).build();
        }

        questRepository.deleteById((long) id);


        return ResponseEntity.status(200).build();
    }


    @Override
    public ResponseEntity<Quest> endQuest(Integer id) {

        QuestEntity questEntity = checkIfExistAndReturnQuest(id);

        if (questEntity == null) {
            return ResponseEntity.status(400).build();
        }

        if (validateAccount(questEntity.getOwner())) {
            return ResponseEntity.status(401).build();
        }

        questEntity.setEnded(true);

        questRepository.save(questEntity);


        return ResponseEntity.status(200).body(questEntity.toQuest());


    }

    @Override
    public ResponseEntity<Quest> getQuest(Integer id) {

        return questRepository.findById((long) id)
                .map(questEntity->ResponseEntity.status(200).body(questEntity.toQuest()))
                .orElseGet(()->ResponseEntity.status(404).build());
    }

    @Override
    public ResponseEntity<List<Quest>> getQuests(@Valid String limitValue, @Valid String pageValue) {

        Integer limit = toNumber(limitValue);
        Integer page = toNumber(pageValue);

        LinkedList<Quest> quests = new LinkedList<>();
        if (limit != null) {
            questRepository.findAll(PageRequest.of(page != null ? page : 0, limit))
                    .forEach(entity -> quests.add(entity.toQuest()));
        } else {
            questRepository.findAll()
                    .forEach(entity -> quests.add(entity.toQuest()));
        }

        return ResponseEntity.ok(quests);
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
    public ResponseEntity<Quest> updateQuest(Integer id, @Valid QuestUpdate informations) {

        QuestEntity questEntity = checkIfExistAndReturnQuest(id);

        if (questEntity == null) {
            return ResponseEntity.status(400).build();
        }


        if (validateAccount(questEntity.getOwner())) {
            return ResponseEntity.status(401).build();
        }

        questEntity.setTitle(informations.getTitle());
        questEntity.setDescription(informations.getDescription());

        questRepository.save(questEntity);

        return ResponseEntity.status(200).build();
    }


    private QuestEntity checkIfExistAndReturnQuest(Integer id) {
        Optional<QuestEntity> quest = questRepository.findById((long) id);
        return quest.orElse(null);
    }

    private boolean validateAccount(AdventurerEntity owner) {
        String tokenEmail = (String) request.getAttribute(JwtFilterAdv.EMAIL_REQUEST_ATTRIBUTE);
        if (tokenEmail == null || !tokenEmail.equals(owner.getUserEmail())) {
            return true;
        }
        return false;
    }

}
