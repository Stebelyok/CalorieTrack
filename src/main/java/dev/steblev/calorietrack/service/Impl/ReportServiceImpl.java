package dev.steblev.calorietrack.service.Impl;

import dev.steblev.calorietrack.dto.report.DailyCalorieReportDto;
import dev.steblev.calorietrack.dto.report.DailyMealHistoryDto;
import dev.steblev.calorietrack.dto.report.ReportMealDto;
import dev.steblev.calorietrack.exception.EntityNotFoundException;
import dev.steblev.calorietrack.exception.ExceptionMessage;
import dev.steblev.calorietrack.model.Dish;
import dev.steblev.calorietrack.model.Meal;
import dev.steblev.calorietrack.model.User;
import dev.steblev.calorietrack.repository.MealRepository;
import dev.steblev.calorietrack.repository.UserRepository;
import dev.steblev.calorietrack.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {
    private final MealRepository mealRepository;
    private final HarrisBenedictCalorieCalculatorServiceImpl calorieCalculator;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public DailyCalorieReportDto getDailyCalorieReport(Long userId, LocalDate date) {
        List<Meal> dailyMeals = getMealsForUserBetweenDates(userId, date, date);

        Double totalCalories = calculateTotalCalories(dailyMeals);

        List<ReportMealDto> reportMealDtos = dailyMeals.stream()
                .map(meal -> ReportMealDto.builder()
                        .mealType(meal.getMealType())
                        .calorie(meal.getDishes().stream()
                                .mapToDouble(Dish::getCaloriePerServing)
                                .sum())
                        .dishes(meal.getDishes().stream()
                                .map(Dish::getName)
                                .toList())
                        .build())
                .toList();

        return DailyCalorieReportDto.builder()
                .totalCalories(totalCalories)
                .totalMeals(dailyMeals.size())
                .date(date)
                .meals(reportMealDtos)
                .build();
    }

    @Transactional(readOnly = true)
    public boolean checkIfUserStayedWithinCalorieLimit(Long userId, LocalDate date) {
        User user = getUser(userId);
        double limit = calorieCalculator.calculateCalorie(user);

        List<Meal> dailyMeals = getMealsForUserBetweenDates(userId, date, date);

        double totalCalories = calculateTotalCalories(dailyMeals);

        return totalCalories <= limit;
    }

    @Transactional(readOnly = true)
    public List<DailyMealHistoryDto> getUserMealHistoryByDate(Long userId, LocalDate startDate, LocalDate endDate) {
        List<Meal> dailyMeals = getMealsForUserBetweenDates(userId, startDate, endDate);

        Map<LocalDate, List<Meal>> mealsGroupedByDate = dailyMeals.stream()
                .collect(Collectors.groupingBy(meal -> meal.getDate().toLocalDate()));

        return mealsGroupedByDate.entrySet().stream()
                .map(this::createDailyMealHistoryDto)
                .collect(Collectors.toList());
    }

    private List<Meal> getMealsForUserBetweenDates(Long userId, LocalDate startDate, LocalDate endDate) {
        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.atTime(LocalTime.MAX);

        List<Meal> dailyMeals = mealRepository.findByUserIdAndDateBetween(userId, startDateTime, endDateTime);

        if (dailyMeals.isEmpty()) {
            throw new EntityNotFoundException(ExceptionMessage.MEALS_NOT_FOUND);
        }

        return dailyMeals;
    }

    private Double calculateTotalCalories(List<Meal> dailyMeals) {
        return dailyMeals.stream()
                .flatMap(meal -> meal.getDishes().stream())
                .mapToDouble(Dish::getCaloriePerServing)
                .sum();
    }

    private User getUser(Long userId) {
        return userRepository.findUserById(userId)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format(ExceptionMessage.USER_NOT_FOUND, userId)));
    }

    private DailyMealHistoryDto createDailyMealHistoryDto(Map.Entry<LocalDate, List<Meal>> entry) {
        double totalCalories = entry.getValue().stream()
                .flatMap(meal -> meal.getDishes().stream())
                .mapToDouble(Dish::getCaloriePerServing)
                .sum();

        List<ReportMealDto> mealDtos = entry.getValue().stream()
                .map(meal -> ReportMealDto.builder()
                        .mealType(meal.getMealType())
                        .calorie(meal.getDishes().stream()
                                .mapToDouble(Dish::getCaloriePerServing)
                                .sum())
                        .dishes(meal.getDishes().stream()
                                .map(Dish::getName)
                                .collect(Collectors.toList()))
                        .build())
                .collect(Collectors.toList());

        return DailyMealHistoryDto.builder()
                .date(entry.getKey())
                .totalCalories(totalCalories)
                .meals(mealDtos)
                .build();
    }
}
