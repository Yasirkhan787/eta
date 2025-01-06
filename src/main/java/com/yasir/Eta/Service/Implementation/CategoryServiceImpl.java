package com.yasir.Eta.Service.Implementation;

import com.yasir.Eta.Entities.Category;
import com.yasir.Eta.Entities.User;
import com.yasir.Eta.Repositories.CategoryRepository;
import com.yasir.Eta.Requests.CategoryRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }


    // Create a new category
    public void createCategory(CategoryRequest request, User user) {

        /*Category category = Category.builder()
                .name(request.getName())
                .type(request.getType())
                .user(user)
                .build();*/

        Category category = new Category();
        category.setName(request.getName());
        category.setType(request.getType());
        category.setUser(user);


        categoryRepository.save(category);
    }

    // Update a category
    public void updateCategory(Long id, CategoryRequest request, User user) {

        Category category = categoryRepository.findByIdAndUser(id, user);
        if (category == null) {
            return;
        }

        category.setName(request.getName());
        category.setType(request.getType());

        categoryRepository.save(category);
    }

    // Delete a category
    public void deleteCategory(Long id, User user) {
        Category category = categoryRepository.findByIdAndUser(id, user);
        if (category == null) {
            return;
        }

        categoryRepository.delete(category);
    }

    // Get all categories
    //public List<Category> getAllCategories(User user) {
    //     return categoryRepository.findByUserOrIsPredefined(user, false);
    //}

    // Get category by Type
    public List<Category> getCategoriesByTypeAndUser(String type, User user) {
        List<Category> expenseCategories = categoryRepository.findByTypeAndUser(type, user);
        expenseCategories.forEach(category ->
                System.out.println("Category: " + category)
        );

        return expenseCategories;
    }

    // Get Category by Type only
    public List<Category> getCategoriesByType(String type) {
        return categoryRepository.findByType(type);
    }

    // Get all categories including predefined categories
    public List<Category> getAllCategories(User user) {
        return categoryRepository.findByUserOrIsPredefined(user, true);
    }

    // Get all Income categories including predefined categories
    public List<Category> getIncomeCategory(User user) {
        return categoryRepository.findByTypeAndUser("Income", user);
    }

    // Get all Expense categories including predefined categories
    public List<Category> getExpenseCategory(User user) {
        return categoryRepository.findByTypeAndUser("Expense", user);
    }

    public Category getCategoryById(Long id) {

        return categoryRepository.findById(id).orElse(null);
    }

    // Add predefined categories for Admin user
    public void addPredefined(CategoryRequest request) {

        Category category = new Category();
        category.setName(request.getName());
        category.setType(request.getType());
        category.setPredefined(true);  // Set predefined to true
        category.setUser(null);        // Set user to null (since it's predefined)

        categoryRepository.save(category);
    }
}
