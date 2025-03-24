package dev.steblev.calorietrack.service;

import dev.steblev.calorietrack.dto.dish.CreateDishDto;
import dev.steblev.calorietrack.dto.dish.DishDto;

public interface DishService {
    DishDto createDish(Long userId, CreateDishDto createDishDto);
}
