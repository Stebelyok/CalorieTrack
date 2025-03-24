package dev.steblev.calorietrack.model;

import lombok.Getter;

@Getter
public enum GenderType {
    MALE("Мужской"),
    FEMALE("Женский");

    private final String rusName;

    GenderType(String rusName) {
        this.rusName = rusName;
    }
}