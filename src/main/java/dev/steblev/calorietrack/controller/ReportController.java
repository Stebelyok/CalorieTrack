package dev.steblev.calorietrack.controller;

import dev.steblev.calorietrack.dto.report.DailyCalorieReportDto;
import dev.steblev.calorietrack.dto.report.DailyMealHistoryDto;
import dev.steblev.calorietrack.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/{userId}/reports")
public class ReportController {
    private final ReportService reportService;

    @GetMapping("/daily-calories")
    public ResponseEntity<DailyCalorieReportDto> dailyCalorieReport(
            @PathVariable Long userId,
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(reportService.getDailyCalorieReport(userId, date));
    }

    @GetMapping("/calorie-limit")
    public ResponseEntity<Boolean> checkIfUserStayedWithinCalorieLimit(
            @PathVariable Long userId,
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(reportService.checkIfUserStayedWithinCalorieLimit(userId, date));
    }

    @GetMapping("/meal-history")
    public ResponseEntity<List<DailyMealHistoryDto>> dailyCalorieReport(
            @PathVariable Long userId,
            @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end) {
        return ResponseEntity.ok(reportService.getUserMealHistoryByDate(userId, start, end));
    }
}