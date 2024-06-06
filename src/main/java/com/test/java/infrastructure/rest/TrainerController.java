package com.test.java.infrastructure.rest;

import com.test.java.application.TrainerService;
import com.test.java.domain.model.Trainer;
import com.test.java.infrastructure.dto.ApiResponseDTO;
import org.apache.commons.collections4.IterableUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/trainers")
public class TrainerController {

    private final TrainerService trainerService;

    public TrainerController(TrainerService trainerService) {
        this.trainerService = trainerService;
    }

    @GetMapping()
    public ResponseEntity<ApiResponseDTO<Object>> findByNameOrMainPokemon(@RequestParam(required = false) String filterText) {
        try {
            Iterable<Trainer> trainers = trainerService.findByNameOrMainPokemon(filterText);
            String message = "Trainers found";
            if (IterableUtils.size(trainers) == 0 && filterText == "") {
                message = "There are no registered trainers";
            } else if (IterableUtils.size(trainers) == 0 && filterText != "") {
                message = "No trainer matches the search value";
            } else if (IterableUtils.size(trainers) > 0 && filterText != "") {
                message = "Trainers that match the search value";
            }
            ApiResponseDTO<Object> response = new ApiResponseDTO<>(200, false, message, trainers);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            ApiResponseDTO<Object> response = new ApiResponseDTO<>(500, true, e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}