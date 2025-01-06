package com.yasir.Eta.Controller;

import com.yasir.Eta.Entities.Category;
import com.yasir.Eta.Entities.User;
import com.yasir.Eta.Requests.CategoryRequest;
import com.yasir.Eta.Service.Implementation.CategoryServiceImpl;
import com.yasir.Eta.Service.Implementation.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/v1/category")
public class CategoryController {

    private final CategoryServiceImpl categoryService;

    private final UserServiceImpl userService;

    @Autowired
    public CategoryController(CategoryServiceImpl categoryService, UserServiceImpl userService) {
        this.categoryService = categoryService;
        this.userService = userService;
    }

    // Show Category
    @GetMapping
    public String showCategory(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            // Redirect to login page if the user is not authenticated
            return "redirect:/login";  // Adjust the login path as needed
        }

        // Get the user from the authentication object
        String username = authentication.getName();
        User user = userService.getUserByUsername(username);

        if (user == null) {
            // Return an error page or redirect as needed if user is not found
            return "error";  // Redirect or return an error template as appropriate
        }

        // Add the user to the model
        model.addAttribute("user", user);

        // Get all categories of Logged-In user and add them to the model
        model.addAttribute("categories", categoryService.getAllCategories(user));

        // Return the name of the Thymeleaf template (category.html)
        return "category";
    }
    // Show Income Category
    @GetMapping("/income")
    public String showIncomeCategory(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            // Redirect to login page if the user is not authenticated
            return "redirect:/login";  // Adjust the login path as needed
        }

        // Get the user from the authentication object
        String username = authentication.getName();
        User user = userService.getUserByUsername(username);

        if (user == null) {
            // Return an error page or redirect as needed if user is not found
            return "error";  // Redirect or return an error template as appropriate
        }

        // Get all Income Categories for the user and add them to the model
        model.addAttribute("categories", categoryService.getCategoriesByTypeAndUser("Income", user));

        // Return the name of the Thymeleaf template (category.html)
        return "category";
    }

    // Show Expense Category
    @GetMapping("/expense")
    public String showExpenseCategory(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            // Redirect to login page if the user is not authenticated
            return "redirect:/login";  // Adjust the login path as needed
        }

        // Get the user from the authentication object
        String username = authentication.getName();
        User user = userService.getUserByUsername(username);

        if (user == null) {
            // Return an error page or redirect as needed if user is not found
            return "error";  // Redirect or return an error template as appropriate
        }

        // Get all Income Categories for the user and add them to the model
        model.addAttribute("categories", categoryService.getCategoriesByTypeAndUser("Expense", user));

        // Return the name of the Thymeleaf template (category.html)
        return "category";
    }

    // Show Add-Category Form
    @GetMapping("/add")
    public String showAddCategory() {
        return "add-category";
    }

    // Create a category
    @PostMapping("/add")
    public ResponseEntity<?> addCategory(@ModelAttribute CategoryRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(401).body("User is not authenticated.");
        }

        // Get the user from the authentication object
        String username = authentication.getName();
        User user = userService.getUserByUsername(username);
        if (user == null) {
            return ResponseEntity.status(404).body("User not found.");
        }

        categoryService.createCategory(request, user);

        return ResponseEntity.status(201).body("Category created successfully.");
    }

    // Update a category
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable Long id, @ModelAttribute CategoryRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(401).body("User is not authenticated.");
        }

        // Get the user from the authentication object
        String username = authentication.getName();
        User user = userService.getUserByUsername(username);
        if (user == null) {
            return ResponseEntity.status(404).body("User not found.");
        }

        categoryService.updateCategory(id, request, user);

        return ResponseEntity.status(200).body("Category updated successfully.");
    }

    // Delete a category
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(401).body("User is not authenticated.");
        }

        // Get the user from the authentication object
        String username = authentication.getName();
        User user = userService.getUserByUsername(username);
        if (user == null) {
            return ResponseEntity.status(404).body("User not found.");
        }

        categoryService.deleteCategory(id, user);

        return ResponseEntity.status(200).body("Category deleted successfully.");
    }

    // Endpoint for adding a predefined category
    // Method to show the category form to the admin
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/add-predefined")
    public String showAddCategoryForm(Model model) {
        // Render the form for adding a predefined category
        return "add-category"; // This will render the 'add-category.html' template
    }

    // Method to handle form submission and add the predefined category
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/add-predefined")
    public String addPredefinedCategory(@ModelAttribute CategoryRequest request) {

        // Save the category to the database
        categoryService.addPredefined(request);

        // Redirect to a confirmation page or category list after adding
        return "redirect:/v1/category"; // Or redirect to the list of categories page
    }
}



