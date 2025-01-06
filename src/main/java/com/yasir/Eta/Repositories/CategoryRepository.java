package com.yasir.Eta.Repositories;

import com.yasir.Eta.Entities.Category;
import com.yasir.Eta.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    // Find all categories by user or predefined categories
    List<Category> findByUserOrIsPredefined(User user, Boolean isPredefined);

    // Find all categories by type
    List<Category> findByType(String type);

    // Find all categories by type and user
    List<Category> findByTypeAndUser(String type, User user);

    // Find category by ID and user
    Category findByIdAndUser(Long id, User user);

    // Find category by ID
    Optional<Category> findById(Long id);

    // Find category by ID and user or predefined category
    Category findByIdAndUserOrIsPredefined(Long id, User user, Boolean isPredefined);
}
