package dev.steblev.calorietrack.repository;

import dev.steblev.calorietrack.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserById(Long id);

    Boolean existsByEmail(String email);
}
