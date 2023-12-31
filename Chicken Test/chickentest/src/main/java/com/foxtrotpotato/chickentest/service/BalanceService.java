package com.foxtrotpotato.chickentest.service;

import com.foxtrotpotato.chickentest.entity.Balance;

import java.util.List;

public interface BalanceService {

    List<Balance> findAll();

    Balance findById(int theId);

    void save(Balance theBalance);

    Double getLastBalance();

    int countSalesBalances();

    int countPurchasesBalances();

    Double sumPurchasesBalances();

    Double sumSalesBalances();
}
