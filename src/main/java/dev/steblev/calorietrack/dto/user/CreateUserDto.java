package dev.steblev.calorietrack.dto.user;

import dev.steblev.calorietrack.model.GenderType;
import dev.steblev.calorietrack.model.GoalType;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CreateUserDto {
    @NotBlank(message = "Имя не может быть пустым.")
    @Size(min = 2, max = 50, message = "Имя должно быть от 2 до 50 символов.")
    private String name;

    @Email(message = "Некорректный формат email.")
    @NotBlank(message = "Email не может быть пустым.")
    @Size(max = 100, message = "Email не должен превышать 100 символов.")
    private String email;

    @Min(value = 1, message = "Возраст должен быть больше 0.")
    @Max(value = 120, message = "Возраст не может превышать 120 лет.")
    @NotNull(message = "Возраст не может быть пустым.")
    private Integer age;

    @Min(value = 1, message = "Вес должен быть больше 0.")
    @Max(value = 500, message = "Вес не может превышать 500 кг.")
    @NotNull(message = "Вес не может быть пустым.")
    @Digits(integer = 3, fraction = 2, message = "Вес должен иметь не более 2 знаков после запятой.")
    private Double weight;

    @Min(value = 50, message = "Рост должен быть хотя бы 50 см.")
    @Max(value = 300, message = "Рост не может превышать 300 см.")
    @NotNull(message = "Рост не может быть пустым.")
    @Digits(integer = 3, fraction = 2, message = "Вес должен иметь не более 2 знаков после запятой.")
    private Double height;

    @NotNull(message = "Цель не может быть пустой.")
    private GoalType goalType;

    @NotNull(message = "Пол не может быть пустым.")
    private GenderType genderType;
}
