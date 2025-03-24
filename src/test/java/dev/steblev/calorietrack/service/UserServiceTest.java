package dev.steblev.calorietrack.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import dev.steblev.calorietrack.dto.user.CreateUserDto;
import dev.steblev.calorietrack.dto.user.UserDto;
import dev.steblev.calorietrack.exception.EntityAlreadyExistsException;
import dev.steblev.calorietrack.mapper.UserMapper;
import dev.steblev.calorietrack.model.User;
import dev.steblev.calorietrack.repository.UserRepository;
import dev.steblev.calorietrack.service.Impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private CalorieCalculatorService calorieCalculatorService;

    @InjectMocks
    private UserServiceImpl userService;

    private CreateUserDto createUserDto;
    private User user;
    private UserDto userDto;

    @BeforeEach
    void setUp() {
        createUserDto = CreateUserDto.builder()
                .email("email@email.com")
                .name("name")
                .build();
        user = User.builder()
                .email("email@email.com")
                .name("name")
                .build();
        userDto = UserDto.builder()
                .email("email@email.com")
                .name("name")
                .calorieNorm(2000.0)
                .build();
    }

    @Test
    void createUser_ShouldCreateUser_WhenEmailDoesNotExist() {
        when(userRepository.existsByEmail(createUserDto.getEmail())).thenReturn(false);
        when(userMapper.createUserDtoToUser(createUserDto)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(user);
        when(userMapper.userToUserDto(user)).thenReturn(userDto);
        when(calorieCalculatorService.calculateCalorie(user)).thenReturn(2000.0);

        UserDto result = userService.createUser(createUserDto);

        assertNotNull(result);
        assertEquals("email@email.com", result.getEmail());
        assertEquals(2000, result.getCalorieNorm());
        verify(userRepository).save(user);
    }

    @Test
    void createUser_ShouldThrowException_WhenEmailAlreadyExists() {
        when(userRepository.existsByEmail(createUserDto.getEmail())).thenReturn(true);

        EntityAlreadyExistsException exception = assertThrows(EntityAlreadyExistsException.class,
                () -> userService.createUser(createUserDto));

        assertEquals("Пользователь с email email@email.com уже существует", exception.getMessage());
        verify(userRepository, never()).save(any());
    }
}
