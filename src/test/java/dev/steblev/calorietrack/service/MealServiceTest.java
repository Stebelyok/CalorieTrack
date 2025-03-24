package dev.steblev.calorietrack.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import dev.steblev.calorietrack.dto.dish.CreateDishDto;
import dev.steblev.calorietrack.dto.dish.DishDto;
import dev.steblev.calorietrack.dto.dish.DishInputDto;
import dev.steblev.calorietrack.dto.meal.CreateMealDto;
import dev.steblev.calorietrack.dto.meal.MealDto;
import dev.steblev.calorietrack.exception.EntityNotFoundException;
import dev.steblev.calorietrack.mapper.DishMapper;
import dev.steblev.calorietrack.mapper.MealMapper;
import dev.steblev.calorietrack.model.Dish;
import dev.steblev.calorietrack.model.Meal;
import dev.steblev.calorietrack.model.MealType;
import dev.steblev.calorietrack.model.User;
import dev.steblev.calorietrack.repository.DishRepository;
import dev.steblev.calorietrack.repository.MealRepository;
import dev.steblev.calorietrack.repository.UserRepository;
import dev.steblev.calorietrack.service.Impl.MealServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class MealServiceTest {

    @Mock
    private MealRepository mealRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private DishRepository dishRepository;

    @Mock
    private MealMapper mealMapper;

    @Mock
    private DishMapper dishMapper;

    @InjectMocks
    private MealServiceImpl mealService;

    private User user;
    private CreateMealDto createMealDto;
    private DishInputDto dishInputDto;
    private Dish dish;
    private MealDto mealDto;
    private Meal meal;
    private LocalDateTime dateTime;

    @BeforeEach
    void setUp() {
        dateTime = LocalDateTime.now();

        user = new User();
        user.setId(1L);
        user.setName("User");

        dish = new Dish();
        dish.setId(1L);
        dish.setName("Pasta");

        dishInputDto = new DishInputDto();
        dishInputDto.setDishId(1L);

        createMealDto = new CreateMealDto();
        createMealDto.setMealType(MealType.BREAKFAST);
        createMealDto.setDishes(Collections.singletonList(dishInputDto));

        mealDto = new MealDto();
        mealDto.setMealType(MealType.BREAKFAST);
        mealDto.setDate(dateTime);

        meal = new Meal();
        meal.setId(1L);
        meal.setDate(dateTime);
        meal.setDishes(Collections.singletonList(dish));
    }

    @Test
    void createMeal_ShouldCreateMeal_WhenValidData() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(dishRepository.findDishById(1L)).thenReturn(Optional.of(dish));
        when(mealMapper.createMealDtoToMeal(createMealDto)).thenReturn(meal);
        when(mealRepository.save(meal)).thenReturn(meal);
        when(mealMapper.mealToMealDto(meal)).thenReturn(mealDto);

        MealDto result = mealService.createMeal(1L, createMealDto);

        assertNotNull(result);
        assertEquals(MealType.BREAKFAST, result.getMealType());
        assertEquals(dateTime, result.getDate());

        verify(mealRepository).save(any(Meal.class));
    }

    @Test
    void createMeal_ShouldThrowException_WhenUserNotFound() {
        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> mealService.createMeal(userId, createMealDto));

        assertEquals("Пользователь c id 1 не найден", exception.getMessage());
    }
}