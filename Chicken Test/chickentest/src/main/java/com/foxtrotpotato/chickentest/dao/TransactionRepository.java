package com.foxtrotpotato.chickentest.dao;

import com.foxtrotpotato.chickentest.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    public List<Transaction> findAllByOrderByTransactionDateAsc();

}
