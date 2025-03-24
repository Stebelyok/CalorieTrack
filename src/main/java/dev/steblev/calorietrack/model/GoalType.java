package dev.steblev.calorietrack.model;

import lombok.Getter;

@Getter
public enum GoalType {
    WEIGHT_LOSS("Похудение", -0.2),
    MAINTENANCE("Поддержание", 0.0),
    MUSCLE_GAIN("Набор массы", 0.2),;

    private final String rusName;
    private final Double coefficient;

    GoalType(String rusName, Double coefficient) {
        this.rusName = rusName;
        this.coefficient = coefficient;
    }
}
