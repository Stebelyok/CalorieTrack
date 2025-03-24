package dev.steblev.calorietrack.dto.dish;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DishDto {
    private Long id;

    private String name;

    private Long caloriePerServing;

    private Double proteins;

    private Double fats;

    private Double carbohydrates;

    private Long userId;
}
