package ch.heigvd.amt.adventurer.repositories;

import ch.heigvd.amt.adventurer.entities.AdventurerEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface AdventurerRepository extends CrudRepository<AdventurerEntity, String> {
    Iterable<AdventurerEntity> findAll(Pageable pageable);
}
