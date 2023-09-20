package com.foxtrotpotato.chickentest.service;

import com.foxtrotpotato.chickentest.entity.TransactionDetail;

import java.util.List;

public interface TransactionDetailService {
    void save(TransactionDetail theTransactionDetail);

    TransactionDetail findById(int theId);

    List<TransactionDetail> findAll();
}
