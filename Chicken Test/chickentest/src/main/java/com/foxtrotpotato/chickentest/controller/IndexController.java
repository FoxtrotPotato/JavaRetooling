package com.foxtrotpotato.chickentest.controller;

import com.foxtrotpotato.chickentest.entity.*;
import com.foxtrotpotato.chickentest.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@Controller
public class IndexController {

    private final TransactionService transactionService;
    private final ProductService productService;
    private final BalanceService balanceService;
    private final EggService eggService;
    private final ChickenService chickenService;
    private final TransactionDetailService transactionDetailService;
    private final ParameterService parameterService;
    private final FarmService farmService;

    @Autowired
    public IndexController(TransactionService transactionService,
                                     TransactionDetailService transactionDetailService,
                                     ProductService productService,
                                     BalanceService balanceService,
                                     EggService eggService,
                                     ChickenService chickenService,
                                     ParameterService parameterService,
                                     FarmService farmService) {
        this.transactionService = transactionService;
        this.transactionDetailService = transactionDetailService;
        this.productService = productService;
        this.balanceService = balanceService;
        this.chickenService = chickenService;
        this.eggService = eggService;
        this.parameterService = parameterService;
        this.farmService = farmService;
    }
/*
    @GetMapping("/")
    public RedirectView redirectToIndex(){
        return new RedirectView("/index.html");
    }
  */
/*
    @GetMapping("/")
    public String redirectToIndex(){
        return "index";
    }
*/
    @GetMapping(value={"/", "/index"})
    public String indexData(Model theModel) {

        List<Transaction> theTransactions = transactionService.findAll();
        theModel.addAttribute("transactions", theTransactions);

        List<TransactionDetail> theTransactionDetails = transactionDetailService.findAll();
        theModel.addAttribute("transactionDetails", theTransactionDetails);

        List<Parameter> theParameters = parameterService.findAll();
        theModel.addAttribute("parameters", theParameters);

        List<Chicken> theChickens = chickenService.findAll();
        theModel.addAttribute("chickens", theChickens);

        List<Egg> theEggs = eggService.findAll();
        theModel.addAttribute("eggs", theEggs);
        //

        String farmName = farmService.getFarmByLoggedUser().getFarmName();
        theModel.addAttribute("farmName", farmName);

        Float lastBalance = balanceService.getLastBalance();
        theModel.addAttribute("lastBalance", lastBalance);

        Float salesSum = balanceService.sumSalesBalances();
        theModel.addAttribute("salesSum", salesSum);

        Float purchasesSum = balanceService.sumPurchasesBalances();
        theModel.addAttribute("purchasesSum", purchasesSum);

        int salesCount = balanceService.countSalesBalances();
        theModel.addAttribute("salesCount", salesCount);

        int purchasesCount = balanceService.countPurchasesBalances();
        theModel.addAttribute("purchasesCount", purchasesCount);

        int eggsCount = productService.findById(1).getProductStock();
        theModel.addAttribute("eggsCount", eggsCount);

        int chickensCount = productService.findById(2).getProductStock();
        theModel.addAttribute("chickensCount", chickensCount);

        return "index";
    }

}
