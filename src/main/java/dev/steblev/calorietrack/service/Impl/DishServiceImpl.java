package dev.steblev.calorietrack.service.Impl;

import dev.steblev.calorietrack.dto.dish.CreateDishDto;
import dev.steblev.calorietrack.dto.dish.DishDto;
import dev.steblev.calorietrack.exception.EntityNotFoundException;
import dev.steblev.calorietrack.exception.ExceptionMessage;
import dev.steblev.calorietrack.mapper.DishMapper;
import dev.steblev.calorietrack.model.Dish;
import dev.steblev.calorietrack.model.User;
import dev.steblev.calorietrack.repository.DishRepository;
import dev.steblev.calorietrack.repository.UserRepository;
import dev.steblev.calorietrack.service.DishService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@Service
@RequiredArgsConstructor
public class DishServiceImpl implements DishService {
    private final UserRepository userRepository;
    private final DishRepository dishRepository;
    private final DishMapper dishMapper;

    @Transactional
    public DishDto createDish(Long userId, CreateDishDto createDishDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format(ExceptionMessage.DISH_NOT_FOUND, userId)));

        Dish dish = dishMapper.createDishDtoToDish(createDishDto);
        dish.setUser(user);

        Dish createdDish = dishRepository.save(dish);

        log.info("Блюдо {} успешно добавлено", createdDish.getName());

        return dishMapper.dishToDishDto(createdDish);
    }
}
