package com.foxtrotpotato.chickentest.controller;

import com.foxtrotpotato.chickentest.entity.Balance;
import com.foxtrotpotato.chickentest.service.BalanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/balances")
public class BalanceController {

    private BalanceService balanceService;

    @Autowired
    public BalanceController(BalanceService theBalanceService) {
        balanceService = theBalanceService;
    }

    @PreAuthorize("hasRole('MANAGER')")
    @GetMapping("/list")
    public String listBalance(Model theModel) {
        List<Balance> theBalances = balanceService.findAll();
        theModel.addAttribute("balances", theBalances);
        return "balances/list-balances";
    }

}
