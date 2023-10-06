package com.foxtrotpotato.chickentest.service.serviceImpl;

import com.foxtrotpotato.chickentest.repository.TransactionDetailRepository;
import com.foxtrotpotato.chickentest.entity.TransactionDetail;
import com.foxtrotpotato.chickentest.service.TransactionDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TransactionDetailServiceImpl implements TransactionDetailService {

    private final TransactionDetailRepository transactionDetailRepository;

    @Autowired
    public TransactionDetailServiceImpl(TransactionDetailRepository theTransactionDetailRepository) {
        transactionDetailRepository = theTransactionDetailRepository;
    }

    @Override
    public List<TransactionDetail> findAll() {
        return transactionDetailRepository.findAllByOrderByTransactionDetailsIdDesc();
    }

    @Override
    public TransactionDetail findById(int theId) {
        Optional<TransactionDetail> result = transactionDetailRepository.findById(theId);
        TransactionDetail theTransactionDetail;

        if (result.isPresent()) {
            theTransactionDetail = result.get();
        }
        else {
            throw new RuntimeException("Did not find Transaction Detail id - " + theId);
        }

        return theTransactionDetail;
    }


    @Override
    public void save(TransactionDetail theTransactionDetail) {
        transactionDetailRepository.save(theTransactionDetail);
    }


}
