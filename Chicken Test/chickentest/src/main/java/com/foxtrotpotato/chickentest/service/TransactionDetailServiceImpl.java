package com.foxtrotpotato.chickentest.service;

import com.foxtrotpotato.chickentest.dao.TransactionDetailRepository;
import com.foxtrotpotato.chickentest.entity.TransactionDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionDetailServiceImpl implements TransactionDetailService {

    private final TransactionDetailRepository transactionDetailRepository;

    @Autowired
    public TransactionDetailServiceImpl(TransactionDetailRepository theTransactionDetailRepository) {
        transactionDetailRepository = theTransactionDetailRepository;
    }

    @Override
    public void save(TransactionDetail theTransactionDetail) {
        transactionDetailRepository.save(theTransactionDetail);
    }
}
