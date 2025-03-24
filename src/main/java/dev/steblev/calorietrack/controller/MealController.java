package dev.steblev.calorietrack.controller;

import dev.steblev.calorietrack.dto.meal.CreateMealDto;
import dev.steblev.calorietrack.dto.meal.MealDto;
import dev.steblev.calorietrack.service.MealService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/{userId}/meals")
@RequiredArgsConstructor
public class MealController {
    private final MealService mealService;

    @PostMapping()
    public ResponseEntity<MealDto> createdMeal(@PathVariable Long userId, @Valid @RequestBody CreateMealDto meal) {
        return ResponseEntity.ok(mealService.createMeal(userId, meal));
    }
}