package ch.heigvd.amt.adventurer.repositories;


import ch.heigvd.amt.adventurer.entities.QuestEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface QuestRepository extends CrudRepository<QuestEntity, String> {

    QuestEntity findQuestEntityByowner(String ownerName);


}
