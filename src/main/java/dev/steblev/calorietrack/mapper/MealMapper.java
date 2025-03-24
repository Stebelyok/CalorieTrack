package dev.steblev.calorietrack.mapper;

import dev.steblev.calorietrack.dto.meal.CreateMealDto;
import dev.steblev.calorietrack.dto.meal.MealDto;
import dev.steblev.calorietrack.dto.dish.DishDto;
import dev.steblev.calorietrack.model.Dish;
import dev.steblev.calorietrack.model.Meal;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MealMapper {
    @Mapping(target = "date", source = "createMealDto.date")
    @Mapping(target = "mealType", source = "createMealDto.mealType")
    @Mapping(target = "dishes", ignore = true)
    Meal createMealDtoToMeal(CreateMealDto createMealDto);

    @Mapping(target = "date", source = "meal.date")
    @Mapping(target = "mealType", source = "meal.mealType")
    @Mapping(target = "dishes", source = "meal.dishes")
    MealDto mealToMealDto(Meal meal);

    @Mapping(target = "id", source = "dish.id")
    @Mapping(target = "name", source = "dish.name")
    @Mapping(target = "caloriePerServing", source = "dish.caloriePerServing")
    @Mapping(target = "proteins", source = "dish.proteins")
    @Mapping(target = "fats", source = "dish.fats")
    @Mapping(target = "carbohydrates", source = "dish.carbohydrates")
    @Mapping(target = "userId", source = "dish.user.id")
    DishDto dishToDishDto(Dish dish);
}
