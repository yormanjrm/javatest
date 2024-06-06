package com.test.java.infrastructure.mapper;

import com.test.java.domain.model.Trainer;
import com.test.java.infrastructure.entity.TrainerEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface TrainerMapper {

    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "name", target = "name"),
            @Mapping(source = "main_pokemon", target = "main_pokemon")
    })

    Trainer toTrainer(TrainerEntity trainerEntity);
    Iterable<Trainer> toTrainers(Iterable<TrainerEntity> trainerEntities);

    @InheritInverseConfiguration
    TrainerEntity toTrainerEntity(Trainer trainer);
    Iterable<TrainerEntity> toTrainerEntities(Iterable<Trainer> trainers);
}