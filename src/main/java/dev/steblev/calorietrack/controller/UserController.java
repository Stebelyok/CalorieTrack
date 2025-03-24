package dev.steblev.calorietrack.controller;

import dev.steblev.calorietrack.dto.user.CreateUserDto;
import dev.steblev.calorietrack.dto.user.UserDto;
import dev.steblev.calorietrack.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody CreateUserDto user) {
        return ResponseEntity.ok(userService.createUser(user));
    }
}
