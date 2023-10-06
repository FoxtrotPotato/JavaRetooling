package com.foxtrotpotato.chickentest.service.serviceImpl;

import com.foxtrotpotato.chickentest.repository.TransactionRepository;
import com.foxtrotpotato.chickentest.entity.Transaction;
import com.foxtrotpotato.chickentest.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TransactionServiceImpl implements TransactionService {

    private TransactionRepository transactionRepository;

    @Autowired
    public TransactionServiceImpl(TransactionRepository theTransactionRepository) {
        transactionRepository = theTransactionRepository;
    }

    @Override
    public List<Transaction> findAll() {
        return transactionRepository.findAllByOrderByTransactionDateDesc();
    }

    @Override
    public Transaction findById(int theId) {
        Optional<Transaction> result = transactionRepository.findById(theId);

        Transaction theTransaction = null;

        if (result.isPresent()) {
            theTransaction = result.get();
        }
        else {
            throw new RuntimeException("Did not find Transaction id - " + theId);
        }

        return theTransaction;
    }

    @Override
    public void save(Transaction theTransaction) {
        transactionRepository.save(theTransaction);
    }

}