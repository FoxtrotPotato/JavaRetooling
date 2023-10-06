package com.foxtrotpotato.chickentest.repository;

import com.foxtrotpotato.chickentest.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    List<Transaction> findAllByOrderByTransactionDateDesc();

}
