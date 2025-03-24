package dev.steblev.calorietrack.dto.user;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class UserDto {
    private Long id;

    private String name;

    private String email;

    private Integer age;

    private Double weight;

    private Double height;

    private String goalType;

    private String genderType;

    private Double calorieNorm;
}
