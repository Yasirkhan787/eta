package com.yasir.Eta.Service.Implementation;

import com.yasir.Eta.Entities.Transaction;
import com.yasir.Eta.Entities.TransactionType;
import com.yasir.Eta.Entities.User;
import com.yasir.Eta.Repositories.CategoryRepository;
import com.yasir.Eta.Repositories.TransactionRepository;
import com.yasir.Eta.Requests.TransactionRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionServiceImpl {

    private final TransactionRepository transactionRepository;
    private final CategoryRepository categoryRepository;
    private final TransactionTypeServiceImpl transactionTypeService;

    @Autowired
    public TransactionServiceImpl(TransactionRepository transactionRepository, CategoryRepository categoryRepository, TransactionTypeServiceImpl transactionTypeService) {
        this.transactionRepository = transactionRepository;
        this.categoryRepository = categoryRepository;
        this.transactionTypeService = transactionTypeService;
    }

    // Get all transactions by type
    public List<Transaction> getTransactionsByType(String type, User user) {
        return transactionRepository.findByCategoryTypeAndUser(type, user);
    }

    // Save transaction
    public void saveTransaction(TransactionRequest transactionRequest, User user) {
        Transaction transaction = new Transaction();

        // Map fields from TransactionRequest to Transaction entity
        transaction.setTitle(transactionRequest.getTitle());
        transaction.setTransactionDate(transactionRequest.getTransactionDate());
        transaction.setAmount(transactionRequest.getAmount());
        transaction.setDescription(transactionRequest.getDescription());
        transaction.setCategory(transactionRequest.getCategory());
        transaction.setTransactionType(transactionRequest.getTransactionType());
        transaction.setUser(user);

        transactionRepository.save(transaction);
    }

    public double getTotalExpense() {
        return 0.0;
    }

    public double getTotalIncome() {
        return 0.0;
    }

    public List<Transaction> getRecentTransactions(User user) {
        transactionRepository.findAllByUser(user);
        return null;
    }
}
