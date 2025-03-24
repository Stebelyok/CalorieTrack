package dev.steblev.calorietrack.service;

import dev.steblev.calorietrack.dto.report.DailyCalorieReportDto;
import dev.steblev.calorietrack.dto.report.DailyMealHistoryDto;

import java.time.LocalDate;
import java.util.List;

public interface ReportService {
    DailyCalorieReportDto getDailyCalorieReport(Long userId, LocalDate date);
    boolean checkIfUserStayedWithinCalorieLimit(Long userId, LocalDate date);
    List<DailyMealHistoryDto> getUserMealHistoryByDate(Long userId, LocalDate startDate, LocalDate endDate);

}
