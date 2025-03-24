package dev.steblev.calorietrack.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import dev.steblev.calorietrack.dto.dish.CreateDishDto;
import dev.steblev.calorietrack.dto.dish.DishDto;
import dev.steblev.calorietrack.dto.user.CreateUserDto;
import dev.steblev.calorietrack.dto.user.UserDto;
import dev.steblev.calorietrack.exception.EntityNotFoundException;
import dev.steblev.calorietrack.mapper.DishMapper;
import dev.steblev.calorietrack.model.Dish;
import dev.steblev.calorietrack.model.User;
import dev.steblev.calorietrack.repository.DishRepository;
import dev.steblev.calorietrack.repository.UserRepository;
import dev.steblev.calorietrack.service.Impl.DishServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class DishServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private DishRepository dishRepository;

    @Mock
    private DishMapper dishMapper;

    @InjectMocks
    private DishServiceImpl dishService;

    private User user;
    private CreateDishDto createDishDto;
    private Dish dish;
    private DishDto dishDto;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setName("User");

        createDishDto = new CreateDishDto();
        createDishDto.setName("Pasta");

        dish = new Dish();
        dish.setId(1L);
        dish.setName("Pasta");

        dishDto = new DishDto();
        dishDto.setName("Pasta");
    }

    @Test
    void createDish_ShouldCreateDish_WhenUserExists() {
        when(userRepository.findById(1L)).thenReturn(java.util.Optional.of(user));
        when(dishMapper.createDishDtoToDish(createDishDto)).thenReturn(dish);
        when(dishRepository.save(dish)).thenReturn(dish);
        when(dishMapper.dishToDishDto(dish)).thenReturn(dishDto);

        DishDto result = dishService.createDish(1L, createDishDto);

        assertNotNull(result);
        assertEquals("Pasta", result.getName());

        verify(userRepository).findById(1L);
        verify(dishRepository).save(dish);
        verify(dishMapper).createDishDtoToDish(createDishDto);
        verify(dishMapper).dishToDishDto(dish);
    }

    @Test
    void createDish_ShouldThrowException_WhenUserNotFound() {
        when(userRepository.findById(1L)).thenReturn(java.util.Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            dishService.createDish(1L, createDishDto);
        });

        assertEquals("Блюдо c id 1 не найден", exception.getMessage());

        verify(dishRepository, never()).save(any());
    }
}
