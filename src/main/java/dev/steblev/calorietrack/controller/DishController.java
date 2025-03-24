package dev.steblev.calorietrack.controller;

import dev.steblev.calorietrack.dto.dish.CreateDishDto;
import dev.steblev.calorietrack.dto.dish.DishDto;
import dev.steblev.calorietrack.service.DishService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/{userId}/dishes")
@RequiredArgsConstructor
public class DishController {
    private final DishService dishService;

    @PostMapping()
    public ResponseEntity<DishDto> createdDish(@PathVariable Long userId, @Valid @RequestBody CreateDishDto dish) {
        return ResponseEntity.ok(dishService.createDish(userId, dish));
    }
}
