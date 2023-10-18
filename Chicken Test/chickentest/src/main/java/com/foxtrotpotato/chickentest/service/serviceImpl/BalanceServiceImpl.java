package com.foxtrotpotato.chickentest.service.serviceImpl;

import com.foxtrotpotato.chickentest.repository.BalanceRepository;
import com.foxtrotpotato.chickentest.entity.Balance;
import com.foxtrotpotato.chickentest.service.BalanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class BalanceServiceImpl implements BalanceService {

    private final BalanceRepository balanceRepository;

    @Autowired
    public BalanceServiceImpl(BalanceRepository theBalanceRepository) {
        balanceRepository = theBalanceRepository;
    }

    @Override
    public List<Balance> findAll() {
        return balanceRepository.findAllByOrderByBalanceIdDesc();
    }

    @Override
    public Balance findById(int theId) {
        Optional<Balance> result = balanceRepository.findById(theId);

        Balance theBalance;

        if (result.isPresent()) {
            theBalance = result.get();
        } else {
            throw new RuntimeException("Did not find Balance id - " + theId);
        }

        return theBalance;
    }

    @Override
    @Transactional
    public void save(Balance theBalance) {
        balanceRepository.save(theBalance);
    }

    @Override
    public Double getLastBalance() {
        Double lastBalance;
        List<Balance> balanceList = findAll();
        if (!balanceList.isEmpty()) {
            Balance tempBalance = balanceList.get(0);
            lastBalance = tempBalance.getBalanceTotal();
        } else {
            lastBalance = 0d;
        }
        return lastBalance;
    }

    @Override
    public int countSalesBalances() {
        int salesBalances = 0;
        List<Balance> balanceList = findAll();
        if (!balanceList.isEmpty()) {
            for (Balance balance : balanceList) {
                if (balance.getBalanceType().equals("SALE")){
                    salesBalances++;
                }
            }
        }
        return salesBalances;
    }

    @Override
    public int countPurchasesBalances() {
        int purchasesBalances = 0;
        List<Balance> balanceList = findAll();
        if (!balanceList.isEmpty()) {
            for (Balance balance : balanceList) {
                if (balance.getBalanceType().equals("PURCHASE")){
                    purchasesBalances++;
                }
            }
        }
        return purchasesBalances;
    }

    @Override
    public Double sumSalesBalances() {
        double totalSales = 0d;
        List<Balance> balanceList = findAll();
        if (!balanceList.isEmpty()) {
            for (Balance balance : balanceList) {
                if (balance.getBalanceType().equals("SALE")){
                    totalSales = totalSales + balance.getTransaction().getTransactionTotal();
                }
            }
        }
        return totalSales;
    }

    @Override
    public Double sumPurchasesBalances() {
        double totalPurchases = 0d;
        List<Balance> balanceList = findAll();
        if (!balanceList.isEmpty()) {
            for (Balance balance : balanceList) {
                if (balance.getBalanceType().equals("PURCHASE")){
                    totalPurchases = totalPurchases + balance.getTransaction().getTransactionTotal();
                }
            }
        }
        return totalPurchases;
    }

}