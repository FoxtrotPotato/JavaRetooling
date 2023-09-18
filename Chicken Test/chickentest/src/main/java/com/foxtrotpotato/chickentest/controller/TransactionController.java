package com.foxtrotpotato.chickentest.controller;

import com.foxtrotpotato.chickentest.entity.Transaction;
import com.foxtrotpotato.chickentest.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/transactions")
public class TransactionController {

    private TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService theTransactionService) {
        transactionService = theTransactionService;
    }

    @GetMapping("/list")
    public String listTransaction(Model theModel) {

        List<Transaction> theTransactions = transactionService.findAll();

        theModel.addAttribute("transactions", theTransactions);

        return "transactions/list-transactions";
    }

    @GetMapping("/showAddTransactionForm")
    public String showFormForAdd(Model theModel) {

        Transaction theTransaction = new Transaction();
        theModel.addAttribute("transaction", theTransaction);

        return "transactions/transaction-form";
    }

    @GetMapping("/showTransactionDetails")
    public String showFormForUpdate(@RequestParam("transactionId") int theId, Model theModel) {

        Transaction theTransaction = transactionService.findById(theId);
        theModel.addAttribute("transaction", theTransaction);

        return "transactions/transaction-details";
    }


}
