package dev.steblev.calorietrack.dto.report;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DailyCalorieReportDto {
    private Double totalCalories;

    private Integer totalMeals;

    private LocalDate date;

    private List<ReportMealDto> meals;
}
