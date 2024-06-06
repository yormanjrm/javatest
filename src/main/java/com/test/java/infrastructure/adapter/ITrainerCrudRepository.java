package com.test.java.infrastructure.adapter;

import com.test.java.infrastructure.entity.TrainerEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ITrainerCrudRepository extends CrudRepository<TrainerEntity, Integer> {
    @Query("SELECT t FROM TrainerEntity t WHERE (LOWER(t.name) LIKE LOWER(CONCAT('%', :filterText, '%')) OR LOWER(t.main_pokemon) LIKE LOWER(CONCAT('%', :filterText, '%')))")
    Iterable<TrainerEntity> findByNameOrMainPokemon(@Param("filterText") String filterText);
}