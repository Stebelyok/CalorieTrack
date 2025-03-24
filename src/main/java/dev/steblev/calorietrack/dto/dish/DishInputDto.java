package dev.steblev.calorietrack.dto.dish;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DishInputDto {
    private Long dishId;
    private CreateDishDto createDishDto;
}
