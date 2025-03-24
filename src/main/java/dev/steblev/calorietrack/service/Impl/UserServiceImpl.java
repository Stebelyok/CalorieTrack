package dev.steblev.calorietrack.service.Impl;

import dev.steblev.calorietrack.dto.user.CreateUserDto;
import dev.steblev.calorietrack.dto.user.UserDto;
import dev.steblev.calorietrack.exception.EntityAlreadyExistsException;
import dev.steblev.calorietrack.exception.ExceptionMessage;
import dev.steblev.calorietrack.mapper.UserMapper;
import dev.steblev.calorietrack.model.User;
import dev.steblev.calorietrack.repository.UserRepository;
import dev.steblev.calorietrack.service.CalorieCalculatorService;
import dev.steblev.calorietrack.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final CalorieCalculatorService calorieCalculatorService;

    @Transactional
    public UserDto createUser(CreateUserDto createUserDto) {
        if (userRepository.existsByEmail(createUserDto.getEmail())) {
            throw new EntityAlreadyExistsException(String.format(
                    ExceptionMessage.USER_ALREADY_EXISTS, createUserDto.getEmail()));
        }

        User user = userMapper.createUserDtoToUser(createUserDto);

        User createdUser = userRepository.save(user);

        UserDto userDto = userMapper.userToUserDto(createdUser);
        userDto.setCalorieNorm(calorieCalculatorService.calculateCalorie(createdUser));

        log.info("Пользователь с email {} успешно создан", createdUser.getEmail());

        return userDto;
    }
}
