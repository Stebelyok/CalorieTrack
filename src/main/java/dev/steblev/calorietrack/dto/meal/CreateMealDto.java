package dev.steblev.calorietrack.dto.meal;

import dev.steblev.calorietrack.dto.dish.DishInputDto;
import dev.steblev.calorietrack.model.MealType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class CreateMealDto {
    private MealType mealType;

    private LocalDateTime date;

    private List<DishInputDto> dishes;
}
