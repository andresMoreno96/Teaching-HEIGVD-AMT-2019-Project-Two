package ch.heigvd.amt.adventurer.repositories;

import ch.heigvd.amt.adventurer.entities.AdventurerEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AdventurerRepository extends CrudRepository<AdventurerEntity, String> {

    List<AdventurerEntity> findAdventurerEntitiesByName(String name);
}
