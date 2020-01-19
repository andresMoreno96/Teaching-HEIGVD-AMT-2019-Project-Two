package ch.heigvd.amt.adventurer.repositories;


import ch.heigvd.amt.adventurer.entities.AdventurerEntity;
import ch.heigvd.amt.adventurer.entities.QuestEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface QuestRepository extends CrudRepository<QuestEntity, Long> {
    Iterable<QuestEntity> findAll(Pageable pageable);
}
