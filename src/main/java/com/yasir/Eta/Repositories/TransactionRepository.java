package com.yasir.Eta.Repositories;

import com.yasir.Eta.Entities.Category;
import com.yasir.Eta.Entities.Transaction;
import com.yasir.Eta.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findAllByUser(User user);

    // Find all transactions by category type
    List<Transaction> findByCategoryType(String type);

    // Find all transaction by category type and user
    List<Transaction> findByCategoryTypeAndUser(String type, User user);

    Transaction findByIdAndUser(Long id, User user);
}
