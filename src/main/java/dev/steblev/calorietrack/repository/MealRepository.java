package dev.steblev.calorietrack.repository;

import dev.steblev.calorietrack.model.Meal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MealRepository extends JpaRepository<Meal, Long> {
    List<Meal> findByUserIdAndDateBetween(Long user_id, LocalDateTime start, LocalDateTime end);
}