package dev.steblev.calorietrack.repository;

import dev.steblev.calorietrack.model.Dish;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DishRepository extends JpaRepository<Dish, Long> {
    @EntityGraph(attributePaths = {"user"})
    Optional<Dish> findDishById(Long id);
}
