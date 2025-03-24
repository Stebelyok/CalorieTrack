package dev.steblev.calorietrack.dto.dish;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateDishDto {
    @NotBlank(message = "Название не может быть пустым")
    @Size(max = 100, message = "Название должно содержать не более 100 символов")
    private String name;

    @NotNull(message = "Калории на порцию не могут быть пустыми")
    @Min(value = 0, message = "Калории на порцию должны быть больше или равны 0")
    private Long caloriePerServing;

    @NotNull(message = "Белки не могут быть пустыми")
    @DecimalMin(value = "0.0", message = "Белки должны быть больше или равны 0")
    @DecimalMax(value = "500.0", inclusive = true, message = "Белки должны быть меньше или равны 500")
    @Digits(integer = 3, fraction = 2, message = "Вес должен иметь не более 2 знаков после запятой.")
    private Double proteins;

    @NotNull(message = "Жиры не могут быть пустыми")
    @DecimalMin(value = "0.0", message = "Жиры должны быть больше или равны 0")
    @DecimalMax(value = "500.0", inclusive = true, message = "Жиры должны быть меньше или равны 500")
    @Digits(integer = 3, fraction = 2, message = "Вес должен иметь не более 2 знаков после запятой.")
    private Double fats;

    @NotNull(message = "Углеводы не могут быть пустыми")
    @DecimalMin(value = "0.0", message = "Углеводы должны быть больше или равны 0")
    @DecimalMax(value = "500.0", inclusive = true, message = "Углеводы должны быть меньше или равны 500")
    @Digits(integer = 3, fraction = 2, message = "Вес должен иметь не более 2 знаков после запятой.")
    private Double carbohydrates;
}
