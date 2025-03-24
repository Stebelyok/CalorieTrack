package dev.steblev.calorietrack.service;

import dev.steblev.calorietrack.dto.user.CreateUserDto;
import dev.steblev.calorietrack.dto.user.UserDto;

public interface UserService {
    UserDto createUser(CreateUserDto createUserDto);
}
