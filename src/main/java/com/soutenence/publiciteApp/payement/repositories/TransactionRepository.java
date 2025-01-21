package com.soutenence.publiciteApp.payement.repositories;

import com.soutenence.publiciteApp.payement.entite.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface TransactionRepository extends JpaRepository<Transaction,Long>, PagingAndSortingRepository<Transaction,Long> {
    Transaction findByTransactionId(String transactionId);
}
