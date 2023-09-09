package com.foxtrotpotato.chickentest.controller;

import com.foxtrotpotato.chickentest.entity.Balance;
import com.foxtrotpotato.chickentest.entity.User;
import com.foxtrotpotato.chickentest.service.BalanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/balances")
public class BalanceController {

    private BalanceService balanceService;

    @Autowired
    public BalanceController(BalanceService theBalanceService) {
        balanceService = theBalanceService;
    }

    @GetMapping("/list")
    public String listBalance(Model theModel) {

        List<Balance> theBalances = balanceService.findAll();

        theModel.addAttribute("balances", theBalances);

        return "balances/list-balances";
    }

    @GetMapping("/showUpdateBalanceForm")
    public String showFormForUpdate(@RequestParam("balanceId") int theId, Model theModel) {

        Balance theBalance = balanceService.findById(theId);
        theModel.addAttribute("balance", theBalance);

        return "balances/balance-form";
    }

    @PostMapping("/save")
    public String saveBalance(@ModelAttribute("balance") Balance theBalance) {
        balanceService.save(theBalance);
        return "redirect:/balances/list";
    }

}
