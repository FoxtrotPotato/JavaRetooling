package com.foxtrotpotato.chickentest.controller;

import com.foxtrotpotato.chickentest.entity.Transaction;
import com.foxtrotpotato.chickentest.entity.TransactionDetail;
import com.foxtrotpotato.chickentest.service.TransactionDetailService;
import com.foxtrotpotato.chickentest.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/transactions")
public class TransactionController {
    private final TransactionService transactionService;
    private final TransactionDetailService transactionDetailService;

    @Autowired
    public TransactionController(TransactionService theTransactionService,
                                 TransactionDetailService theTransactionDetailService) {
        this.transactionService = theTransactionService;
        this.transactionDetailService = theTransactionDetailService;
    }

    @PreAuthorize("hasRole('EMPLOYEE')")
    @GetMapping("/list")
    public String listTransaction(Model theModel) {
        List<Transaction> theTransactions = transactionService.findAll();
        theModel.addAttribute("transactions", theTransactions);

        List<TransactionDetail> theTransactionDetails = transactionDetailService.findAll();
        theModel.addAttribute("transactionDetails", theTransactionDetails);

        return "transactions/list-transactions";
    }

    @PreAuthorize("hasRole('EMPLOYEE')")
    @GetMapping("/showAddTransactionForm")
    public String showFormForAdd(Model theModel) {
        Transaction theTransaction = new Transaction();
        theModel.addAttribute("transaction", theTransaction);

        return "transactions/transaction-form";
    }

}
