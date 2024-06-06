package com.test.java.domain.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pokemon {
    private int id;
    private String name;

    @JsonProperty("base_experience")
    private int baseExperience;

    private int height;

    @JsonProperty("is_default")
    private boolean isDefault;

    @JsonProperty("location_area_encounters")
    private String locationAreaEncounters;

    private int order;
    private int weight;

    private List<Map<String, Object>> abilities;
    private List<Map<String, Object>> forms;

    @JsonProperty("game_indices")
    private List<Map<String, Object>> gameIndices;

    @JsonProperty("held_items")
    private List<Map<String, Object>> heldItems;

    private List<Map<String, Object>> moves;
    private Map<String, Object> species;
    private Map<String, Object> sprites;
    private List<Map<String, Object>> stats;
    private List<Map<String, Object>> types;
    private Map<String, Object> cries;
}