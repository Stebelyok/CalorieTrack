package dev.steblev.calorietrack.dto.meal;

import dev.steblev.calorietrack.dto.dish.DishDto;
import dev.steblev.calorietrack.model.MealType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class MealDto {
    private MealType mealType;

    private LocalDateTime date;

    private List<DishDto> dishes;
}
