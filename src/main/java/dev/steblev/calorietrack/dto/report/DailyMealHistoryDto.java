package dev.steblev.calorietrack.dto.report;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Builder
public class DailyMealHistoryDto {
    private LocalDate date;
    private double totalCalories;
    private List<ReportMealDto> meals;
}
