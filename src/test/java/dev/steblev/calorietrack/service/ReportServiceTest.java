package dev.steblev.calorietrack.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import dev.steblev.calorietrack.dto.meal.MealDto;
import dev.steblev.calorietrack.dto.report.DailyCalorieReportDto;
import dev.steblev.calorietrack.dto.report.DailyMealHistoryDto;
import dev.steblev.calorietrack.dto.report.ReportMealDto;
import dev.steblev.calorietrack.exception.EntityNotFoundException;
import dev.steblev.calorietrack.model.*;
import dev.steblev.calorietrack.repository.MealRepository;
import dev.steblev.calorietrack.repository.UserRepository;
import dev.steblev.calorietrack.service.Impl.HarrisBenedictCalorieCalculatorServiceImpl;
import dev.steblev.calorietrack.service.Impl.ReportServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class ReportServiceTest {

    @Mock
    private MealRepository mealRepository;

    @Mock
    private HarrisBenedictCalorieCalculatorServiceImpl calorieCalculator;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ReportServiceImpl reportService;

    private User user;
    private Meal meal;
    private Dish dish;
    private DailyCalorieReportDto dailyCalorieReportDto;
    private MealDto mealDto;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setName("User");
        user.setGoalType(GoalType.MAINTENANCE);

        dish = new Dish();
        dish.setId(1L);
        dish.setName("Pasta");
        dish.setCaloriePerServing(300L);

        meal = new Meal();
        meal.setId(1L);
        meal.setDate(LocalDateTime.now());
        meal.setMealType(MealType.BREAKFAST);
        meal.setDishes(Collections.singletonList(dish));

        dailyCalorieReportDto = new DailyCalorieReportDto();
        dailyCalorieReportDto.setTotalCalories(300.0);
        dailyCalorieReportDto.setTotalMeals(1);
        dailyCalorieReportDto.setDate(LocalDate.now());
        dailyCalorieReportDto.setMeals(Collections.singletonList(new ReportMealDto(MealType.BREAKFAST, 300.0, Collections.singletonList("Pasta"))));

        mealDto = new MealDto();
        mealDto.setMealType(MealType.BREAKFAST);
    }

    @Test
    void getDailyCalorieReport_ShouldReturnReport_WhenMealsExist() {
        when(mealRepository.findByUserIdAndDateBetween(anyLong(), any(), any())).thenReturn(Collections.singletonList(meal));

        DailyCalorieReportDto result = reportService.getDailyCalorieReport(1L, LocalDate.now());

        assertNotNull(result);
        assertEquals(1, result.getTotalMeals());
        assertEquals(300.0, result.getTotalCalories());
        assertEquals(MealType.BREAKFAST, result.getMeals().get(0).getMealType());

        verify(mealRepository, times(1)).findByUserIdAndDateBetween(anyLong(), any(), any());
    }

    @Test
    void checkIfUserStayedWithinCalorieLimit_ShouldReturnTrue_WhenCaloriesAreWithinLimit() {
        when(userRepository.findUserById(anyLong())).thenReturn(Optional.of(user));
        when(mealRepository.findByUserIdAndDateBetween(anyLong(), any(), any())).thenReturn(Collections.singletonList(meal));
        when(calorieCalculator.calculateCalorie(user)).thenReturn(1000.0);

        boolean result = reportService.checkIfUserStayedWithinCalorieLimit(1L, LocalDate.now());

        assertTrue(result);
    }

    @Test
    void checkIfUserStayedWithinCalorieLimit_ShouldReturnFalse_WhenCaloriesExceedLimit() {
        when(userRepository.findUserById(anyLong())).thenReturn(Optional.of(user));
        when(mealRepository.findByUserIdAndDateBetween(anyLong(), any(), any())).thenReturn(Collections.singletonList(meal));
        when(calorieCalculator.calculateCalorie(user)).thenReturn(250.0);

        boolean result = reportService.checkIfUserStayedWithinCalorieLimit(1L, LocalDate.now());

        assertFalse(result);
    }

    @Test
    void getUserMealHistoryByDate_ShouldReturnMealHistory_WhenMealsExist() {
        when(mealRepository.findByUserIdAndDateBetween(anyLong(), any(), any())).thenReturn(Collections.singletonList(meal));

        List<DailyMealHistoryDto> result = reportService.getUserMealHistoryByDate(1L, LocalDate.now().minusDays(1), LocalDate.now());

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(300.0, result.get(0).getTotalCalories());
    }

    @Test
    void getMealsForUserBetweenDates_ShouldThrowException_WhenMealsNotFound() {
        when(mealRepository.findByUserIdAndDateBetween(anyLong(), any(), any())).thenReturn(Collections.emptyList());

        assertThrows(EntityNotFoundException.class, () -> {
            reportService.getDailyCalorieReport(1L, LocalDate.now());
        });
    }
}
