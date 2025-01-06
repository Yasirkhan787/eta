package com.yasir.Eta.Service.Implementation;

import com.yasir.Eta.Entities.TransactionType;
import com.yasir.Eta.Repositories.TransactionTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionTypeServiceImpl {

    private final TransactionTypeRepository transactionTypeRepository;

    @Autowired
    public TransactionTypeServiceImpl(TransactionTypeRepository transactionTypeRepository) {
        this.transactionTypeRepository = transactionTypeRepository;
    }

    public List<TransactionType> getAllTransaction() {
        return transactionTypeRepository.findAll();
    }
}
