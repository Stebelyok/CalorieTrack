package dev.steblev.calorietrack.mapper;

import dev.steblev.calorietrack.dto.dish.CreateDishDto;
import dev.steblev.calorietrack.dto.dish.DishDto;
import dev.steblev.calorietrack.model.Dish;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface DishMapper {
    @Mapping(target = "name", source = "createDishDto.name", qualifiedByName = "normalizeName")
    @Mapping(target = "caloriePerServing", source = "createDishDto.caloriePerServing")
    @Mapping(target = "proteins", source = "createDishDto.proteins")
    @Mapping(target = "fats", source = "createDishDto.fats")
    @Mapping(target = "carbohydrates", source = "createDishDto.carbohydrates")
    Dish createDishDtoToDish(CreateDishDto createDishDto);

    @Mapping(target = "id", source = "dish.id")
    @Mapping(target = "name", source = "dish.name")
    @Mapping(target = "caloriePerServing", source = "dish.caloriePerServing")
    @Mapping(target = "proteins", source = "dish.proteins")
    @Mapping(target = "fats", source = "dish.fats")
    @Mapping(target = "carbohydrates", source = "dish.carbohydrates")
    @Mapping(target = "userId", source = "dish.user.id")
    DishDto dishToDishDto(Dish dish);

    @Named("normalizeName")
    default String normalizeName(String name) {
        if (name == null || name.isEmpty()) {
            return name;
        }
        return name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
    }
}
