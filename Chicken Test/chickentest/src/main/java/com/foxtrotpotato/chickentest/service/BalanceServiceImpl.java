package com.foxtrotpotato.chickentest.service;

import com.foxtrotpotato.chickentest.dao.BalanceRepository;
import com.foxtrotpotato.chickentest.entity.Balance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BalanceServiceImpl implements BalanceService{

    private BalanceRepository balanceRepository;

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

        Balance theBalance = null;

        if (result.isPresent()) {
            theBalance = result.get();
        }
        else {
            throw new RuntimeException("Did not find Balance id - " + theId);
        }

        return theBalance;
    }

    @Override
    public void save(Balance theBalance) {
        balanceRepository.save(theBalance);
    }

    @Override
    public Float getLastBalance(){
        float lastBalance;
        List<Balance> balanceList = findAll();
        if (!balanceList.isEmpty()) {
            Balance tempBalance = balanceList.get(0);
            lastBalance = tempBalance.getBalanceTotal();
        } else {
            lastBalance = 0f;
        }
        return lastBalance;
    }
}
