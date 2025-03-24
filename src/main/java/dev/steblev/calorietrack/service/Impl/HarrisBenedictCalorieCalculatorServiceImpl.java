package dev.steblev.calorietrack.service.Impl;

import dev.steblev.calorietrack.constant.CalorieCalculationConstants;
import dev.steblev.calorietrack.model.User;
import dev.steblev.calorietrack.service.CalorieCalculatorService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class HarrisBenedictCalorieCalculatorServiceImpl implements CalorieCalculatorService {
    public Double calculateCalorie(User user) {
        double bmr = switch (user.getGenderType()) {
            case MALE -> CalorieCalculationConstants.MALE_BMR_BASE_HB +
                         (CalorieCalculationConstants.MALE_WEIGHT_COEFFICIENT_HB * user.getWeight()) +
                         (CalorieCalculationConstants.MALE_HEIGHT_COEFFICIENT_HB * user.getHeight()) +
                         (CalorieCalculationConstants.MALE_AGE_COEFFICIENT_HB * user.getAge());
            case FEMALE -> CalorieCalculationConstants.FEMALE_BMR_BASE_HB +
                           (CalorieCalculationConstants.FEMALE_WEIGHT_COEFFICIENT_HB * user.getWeight()) +
                           (CalorieCalculationConstants.FEMALE_HEIGHT_COEFFICIENT_HB * user.getHeight()) +
                           (CalorieCalculationConstants.FEMALE_AGE_COEFFICIENT_HB * user.getAge());
        };

        double calorieNorm = bmr + (bmr * user.getGoalType().getCoefficient());
        return new BigDecimal(calorieNorm).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }
}
