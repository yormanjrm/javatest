package com.test.java.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Trainer {
    private Integer id;
    private String name;
    private String main_pokemon;
}