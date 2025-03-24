package dev.steblev.calorietrack.dto.report;

import dev.steblev.calorietrack.model.MealType;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReportMealDto {
    private MealType mealType;

    private Double calorie;

    private List<String> dishes;
}
