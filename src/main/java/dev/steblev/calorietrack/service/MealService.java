package dev.steblev.calorietrack.service;

import dev.steblev.calorietrack.dto.meal.CreateMealDto;
import dev.steblev.calorietrack.dto.meal.MealDto;

public interface MealService {
    MealDto createMeal(Long userId, CreateMealDto mealDto);
}
