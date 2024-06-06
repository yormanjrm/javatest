package com.test.java.infrastructure.adapter;

import com.test.java.domain.model.Trainer;
import com.test.java.domain.ports.ITrainerRepository;
import com.test.java.infrastructure.mapper.TrainerMapper;
import org.springframework.stereotype.Repository;

@Repository
public class TrainerCrudRepositoryImp implements ITrainerRepository {

    private final ITrainerCrudRepository iTrainerCrudRepository;
    private final TrainerMapper trainerMapper;

    public TrainerCrudRepositoryImp(ITrainerCrudRepository iTrainerCrudRepository, TrainerMapper trainerMapper) {
        this.iTrainerCrudRepository = iTrainerCrudRepository;
        this.trainerMapper = trainerMapper;
    }

    @Override
    public Iterable<Trainer> findByNameOrMainPokemon(String filterText) {
        if(filterText.isEmpty()){
            return trainerMapper.toTrainers(iTrainerCrudRepository.findAll());
        } else {
            return trainerMapper.toTrainers(iTrainerCrudRepository.findByNameOrMainPokemon(filterText));
        }
    }

}