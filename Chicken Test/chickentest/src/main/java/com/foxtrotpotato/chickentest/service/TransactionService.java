package com.foxtrotpotato.chickentest.service;

import com.foxtrotpotato.chickentest.entity.Transaction;

import java.util.List;

public interface TransactionService {
    List<Transaction> findAll();

    Transaction findById(int theId);

    void save(Transaction theTransaction);
}
