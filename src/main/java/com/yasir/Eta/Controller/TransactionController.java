package com.yasir.Eta.Controller;


import com.yasir.Eta.Entities.Category;
import com.yasir.Eta.Entities.Transaction;
import com.yasir.Eta.Entities.TransactionType;
import com.yasir.Eta.Entities.User;
import com.yasir.Eta.Requests.TransactionRequest;
import com.yasir.Eta.Service.Implementation.CategoryServiceImpl;
import com.yasir.Eta.Service.Implementation.TransactionServiceImpl;
import com.yasir.Eta.Service.Implementation.TransactionTypeServiceImpl;
import com.yasir.Eta.Service.Implementation.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/v1/transaction")
public class TransactionController {


    private final TransactionServiceImpl transactionService;

    private final CategoryServiceImpl categoryService;

    private final UserServiceImpl userService;

    private final TransactionTypeServiceImpl transactionTypeService;

    @Autowired
    public TransactionController(TransactionServiceImpl transactionService, CategoryServiceImpl categoryService, UserServiceImpl userService, TransactionTypeServiceImpl transactionTypeService) {
        this.transactionService = transactionService;
        this.categoryService = categoryService;
        this.userService = userService;
        this.transactionTypeService = transactionTypeService;
    }

    // Show Transaction
    // Show All Income Transactions
    @GetMapping("/income")
    public String showIncomeTransactions(Model model) {

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

        // Fetch transactions of type 'Expense'
        List<Transaction> incomeTransactions = transactionService.getTransactionsByType("Income", user);
        model.addAttribute("transactions", incomeTransactions);

        return "income"; // Corresponds to a Thymeleaf template for displaying income transactions
    }

    // Show All Expense Transactions
    @GetMapping("/expense")
    public String showExpenseTransactions(Model model) {

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

        // Fetch transactions of type 'Expense'
        List<Transaction> expenseTransactions = transactionService.getTransactionsByType("Expense", user);
        model.addAttribute("transactions", expenseTransactions);

        return "expense"; // Corresponds to the Thymeleaf template for displaying expenses
    }

    // Show Add Income Form
    @GetMapping("/add-income")
    public String showAddIncomeForm(Model model) {

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

        // Get all income categories of Logged-In user
        List<Category> incomeCategories = categoryService.getCategoriesByTypeAndUser("Income", user);
        // Get all transaction types
        List<TransactionType> transactionTypes = transactionTypeService.getAllTransaction();
        // Add the TransactionRequest object to the model
        model.addAttribute("transactionRequest", new TransactionRequest());
        // Add the income categories to the model
        model.addAttribute("incomeCategories", incomeCategories);
        // Add the transaction types to the model
        model.addAttribute("transactionMethods", transactionTypes);

        return "add-income";
    }

    // Handle Add Income
    @PostMapping("/add-income")
    public ResponseEntity<?> saveIncomeTransaction(@ModelAttribute TransactionRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(401).body("User is not authenticated.");
        }

        try {
            String username = authentication.getName();
            User user = userService.getUserByUsername(username);

            if (user == null) {
                return ResponseEntity.status(404).body("User not found.");
            }

            // Ensure category exists and belongs to the user (or is predefined)
            Category category = categoryService.getCategoryById(request.getCategory().getId());
            if (category == null) {
                return ResponseEntity.status(404).body("Category not found.");
            }

            // Ensure transactionType is set
            if (request.getTransactionType() == null) {
                return ResponseEntity.status(400).body("Transaction Type is required.");
            }

            // Save the transaction
            transactionService.saveTransaction(request, user);

            return ResponseEntity.status(201).body("Income transaction added successfully.");
        } catch (Exception ex) {
            return ResponseEntity.status(500).body("An error occurred: " + ex.getMessage());
        }
    }


    // Show Add Expense Form
    @GetMapping("/add-expense")
    public String addExpenseForm(Model model) {

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

        // Get all income categories
        List<Category> expenseCategories = categoryService.getCategoriesByTypeAndUser("Expense", user);
        // Get all transaction types
        List<TransactionType> transactionTypes = transactionTypeService.getAllTransaction();
        // Add the TransactionRequest object to the model
        model.addAttribute("transactionRequest", new TransactionRequest());
        // List of predefined or user-defined expense categories
        model.addAttribute("expenseCategories", expenseCategories);
        // List of predefined transaction types
        model.addAttribute("transactionTypes", transactionTypes);

        return "add-expense"; // Corresponds to the Add Expense form template
    }

    // Handle Add Expense
    @PostMapping("/add-expense")
    public ResponseEntity<?> saveExpenseTransaction(@ModelAttribute TransactionRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(401).body("User is not authenticated.");
        }

        try {
            String username = authentication.getName();
            User user = userService.getUserByUsername(username);

            if (user == null) {
                return ResponseEntity.status(404).body("User not found.");
            }

            // Ensure category exists and belongs to the user (or is predefined)
            Category category = categoryService.getCategoryById(request.getCategory().getId());
            if (category == null) {
                return ResponseEntity.status(404).body("Category not found.");
            }

            // Ensure transactionType is set
            if (request.getTransactionType() == null) {
                return ResponseEntity.status(400).body("Transaction Type is required.");
            }

            // Save the transaction
            transactionService.saveTransaction(request, user);

            return ResponseEntity.status(201).body("Expense transaction added successfully.");
        } catch (Exception ex) {
            return ResponseEntity.status(500).body("An error occurred: " + ex.getMessage());
        }
    }

}






