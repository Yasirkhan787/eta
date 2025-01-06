package com.yasir.Eta.Repositories;

import com.yasir.Eta.Entities.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionTypeRepository extends JpaRepository<TransactionType, Long> {
    TransactionType findByType(String type);
}
