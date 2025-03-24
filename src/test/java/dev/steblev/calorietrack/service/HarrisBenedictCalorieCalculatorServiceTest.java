package dev.steblev.calorietrack.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import dev.steblev.calorietrack.constant.CalorieCalculationConstants;
import dev.steblev.calorietrack.model.GenderType;
import dev.steblev.calorietrack.model.GoalType;
import dev.steblev.calorietrack.model.User;
import dev.steblev.calorietrack.service.Impl.HarrisBenedictCalorieCalculatorServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.math.RoundingMode;

@ExtendWith(MockitoExtension.class)
public class HarrisBenedictCalorieCalculatorServiceTest {
    @InjectMocks
    private HarrisBenedictCalorieCalculatorServiceImpl calorieCalculatorService;

    private User maleUser;
    private User femaleUser;

    @BeforeEach
    void setUp() {
        maleUser = new User();
        maleUser.setGenderType(GenderType.MALE);
        maleUser.setWeight(70.0);
        maleUser.setHeight(175.0);
        maleUser.setAge(30);
        maleUser.setGoalType(GoalType.MAINTENANCE);

        femaleUser = new User();
        femaleUser.setGenderType(GenderType.FEMALE);
        femaleUser.setWeight(60.0);
        femaleUser.setHeight(160.0);
        femaleUser.setAge(28);
        femaleUser.setGoalType(GoalType.WEIGHT_LOSS);
    }

    @Test
    void calculateCalorie_ShouldReturnCorrectValue_ForMaleUser() {
        Double result = calorieCalculatorService.calculateCalorie(maleUser);

        double bmr = CalorieCalculationConstants.MALE_BMR_BASE_HB +
                     (CalorieCalculationConstants.MALE_WEIGHT_COEFFICIENT_HB * maleUser.getWeight()) +
                     (CalorieCalculationConstants.MALE_HEIGHT_COEFFICIENT_HB * maleUser.getHeight()) +
                     (CalorieCalculationConstants.MALE_AGE_COEFFICIENT_HB * maleUser.getAge());

        double expectedCalorie = bmr + (bmr * maleUser.getGoalType().getCoefficient());
        expectedCalorie = new BigDecimal(expectedCalorie).setScale(2, RoundingMode.HALF_UP).doubleValue();

        assertEquals(expectedCalorie, result, 0.01);
    }

    @Test
    void calculateCalorie_ShouldReturnCorrectValue_ForFemaleUser() {
        Double result = calorieCalculatorService.calculateCalorie(femaleUser);

        double bmr = CalorieCalculationConstants.FEMALE_BMR_BASE_HB +
                     (CalorieCalculationConstants.FEMALE_WEIGHT_COEFFICIENT_HB * femaleUser.getWeight()) +
                     (CalorieCalculationConstants.FEMALE_HEIGHT_COEFFICIENT_HB * femaleUser.getHeight()) +
                     (CalorieCalculationConstants.FEMALE_AGE_COEFFICIENT_HB * femaleUser.getAge());

        double expectedCalorie = bmr + (bmr * femaleUser.getGoalType().getCoefficient());
        expectedCalorie = new BigDecimal(expectedCalorie).setScale(2, RoundingMode.HALF_UP).doubleValue();

        assertEquals(expectedCalorie, result, 0.01);
    }
}
