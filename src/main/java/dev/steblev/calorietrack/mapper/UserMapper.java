package dev.steblev.calorietrack.mapper;

import dev.steblev.calorietrack.dto.user.CreateUserDto;
import dev.steblev.calorietrack.dto.user.UserDto;
import dev.steblev.calorietrack.model.GenderType;
import dev.steblev.calorietrack.model.GoalType;
import dev.steblev.calorietrack.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "name", source = "createUserDto.name", qualifiedByName = "normalizeName")
    @Mapping(target = "email", source = "createUserDto.email", qualifiedByName = "toLowerCase")
    @Mapping(target = "age", source = "createUserDto.age")
    @Mapping(target = "weight", source = "createUserDto.weight")
    @Mapping(target = "height", source = "createUserDto.height")
    @Mapping(target = "goalType", source = "createUserDto.goalType")
    @Mapping(target = "genderType", source = "createUserDto.genderType")
    User createUserDtoToUser(CreateUserDto createUserDto);

    @Mapping(target = "id", source = "user.id")
    @Mapping(target = "name", source = "user.name")
    @Mapping(target = "email", source = "user.email")
    @Mapping(target = "age", source = "user.age")
    @Mapping(target = "weight", source = "user.weight")
    @Mapping(target = "height", source = "user.height")
    @Mapping(target = "goalType", source = "user.goalType", qualifiedByName = "toRussianGoalType")
    @Mapping(target = "genderType", source = "user.genderType", qualifiedByName = "toRussianGenderType")
    UserDto userToUserDto(User user);

    List<UserDto> usersToUserDtos(List<User> users);

    @Named("toLowerCase")
    default String toLowerCase(String email) {
        return email != null ? email.toLowerCase() : null;
    }

    @Named("normalizeName")
    default String normalizeName(String name) {
        if (name == null || name.isEmpty()) {
            return name;
        }
        return name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
    }

    @Named("toRussianGoalType")
    default String toRussianGoalType(GoalType goalType) {
        if (goalType != null) {
            return goalType.getRusName();
        }
        return null;
    }

    @Named("toRussianGenderType")
    default String toRussianGenderType(GenderType goalType) {
        if (goalType != null) {
            return goalType.getRusName();
        }
        return null;
    }
}
