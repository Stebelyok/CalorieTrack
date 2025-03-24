package dev.steblev.calorietrack.service.Impl;

import dev.steblev.calorietrack.dto.meal.CreateMealDto;
import dev.steblev.calorietrack.dto.meal.MealDto;
import dev.steblev.calorietrack.dto.dish.DishInputDto;
import dev.steblev.calorietrack.exception.EntityNotFoundException;
import dev.steblev.calorietrack.exception.ExceptionMessage;
import dev.steblev.calorietrack.mapper.DishMapper;
import dev.steblev.calorietrack.mapper.MealMapper;
import dev.steblev.calorietrack.model.Dish;
import dev.steblev.calorietrack.model.Meal;
import dev.steblev.calorietrack.model.User;
import dev.steblev.calorietrack.repository.DishRepository;
import dev.steblev.calorietrack.repository.MealRepository;
import dev.steblev.calorietrack.repository.UserRepository;
import dev.steblev.calorietrack.service.MealService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Log4j2
@Service
@RequiredArgsConstructor
public class MealServiceImpl implements MealService {
    private final MealRepository mealRepository;
    private final UserRepository userRepository;
    private final DishRepository dishRepository;
    private final MealMapper mealMapper;
    private final DishMapper dishMapper;

    @Transactional
    public MealDto createMeal(Long userId, CreateMealDto createMealDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format(ExceptionMessage.USER_NOT_FOUND, userId)));

        List<Dish> dishes = new ArrayList<>();

        for (DishInputDto dishInputDto : createMealDto.getDishes()) {
            if (dishInputDto.getDishId() != null) {
                Dish dish = dishRepository.findDishById(dishInputDto.getDishId())
                        .orElseThrow(() -> new EntityNotFoundException(
                                String.format(ExceptionMessage.DISH_NOT_FOUND, dishInputDto.getDishId())));
                dishes.add(dish);
            } else if (dishInputDto.getCreateDishDto() != null) {
                Dish newDish = dishMapper.createDishDtoToDish(dishInputDto.getCreateDishDto());
                newDish.setUser(user);
                dishes.add(newDish);
            }
        }

        Meal meal = mealMapper.createMealDtoToMeal(createMealDto);
        meal.setUser(user);
        meal.setDishes(dishes);

        Meal createdMeal = mealRepository.save(meal);

        log.info("Прием пищи успешно добавлен {}", createdMeal.getId());

        return mealMapper.mealToMealDto(createdMeal);
    }
}
