package dev.steblev.calorietrack.service;

import dev.steblev.calorietrack.model.User;

public interface CalorieCalculatorService {
    Double calculateCalorie(User user);
}
