package com.yasir.Eta.Requests;

import com.yasir.Eta.Entities.Category;
import com.yasir.Eta.Entities.TransactionType;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionRequest {

    private String title;

    @DateTimeFormat(pattern = "yyyy-MM-dd")  // Specify the date format
    private Date transactionDate;

    private Double amount;

    private Category category;

    private TransactionType transactionType;

    private String description;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
