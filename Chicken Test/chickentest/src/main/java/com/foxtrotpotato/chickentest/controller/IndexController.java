package com.foxtrotpotato.chickentest.controller;

import com.foxtrotpotato.chickentest.entity.*;
import com.foxtrotpotato.chickentest.rest.restservice.TransactionRestService;
import com.foxtrotpotato.chickentest.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.view.RedirectView;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

@Controller
public class IndexController {

    private final TransactionService transactionService;
    private final TransactionRestService transactionRestService;
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
                           TransactionRestService transactionRestService,
                           ProductService productService,
                           BalanceService balanceService,
                           EggService eggService,
                           ChickenService chickenService,
                           ParameterService parameterService,
                           FarmService farmService) {
        this.transactionService = transactionService;
        this.transactionDetailService = transactionDetailService;
        this.transactionRestService = transactionRestService;
        this.productService = productService;
        this.balanceService = balanceService;
        this.chickenService = chickenService;
        this.eggService = eggService;
        this.parameterService = parameterService;
        this.farmService = farmService;
    }

    @GetMapping(value = {"/", "/index"})
    public String redirectToIndex() {
        return "index";
    }


    // chicken deaths and eggs hatching
    @Value("${chicken.lifespan.days}")
    private int chickenLifeSpan;

    @Value("${eggs.lifespan.days}")
    private int eggLifeSpan;

    private int hatchedEggs;
    private int deadChicken;

    @GetMapping("/btn1")
    public String button1() {
        // update date
        hatchEggs();
        killChicken();
        oviposition();

        return "index";
    }

    public void hatchEggs() {
        List<Egg> hatchedEggsList = eggService.checkBirthdays(eggLifeSpan);
        int hatchedEggsQty = (hatchedEggsList != null) ? hatchedEggsList.size() : 0;

        if (hatchedEggsQty > 0) {
            hatchedEggs = hatchedEggs + hatchedEggsQty;
            eggService.deleteList(hatchedEggsList);
            System.out.println("deleted: " + hatchedEggsList);
            for (int i = 0; i < hatchedEggsQty; i++){
                Chicken newChicken = new Chicken(LocalDate.now(), farmService.getFarmByLoggedUser(), productService.findById(2));
                chickenService.save(newChicken);
            }
        }
    }

    public void killChicken() {
        List<Chicken> deadChickenList = chickenService.checkBirthdays(chickenLifeSpan);
        int deadChickenQty = (deadChickenList != null) ? deadChickenList.size() : 0;

        if (deadChickenQty > 0) {
            deadChicken = deadChicken + deadChickenQty;
            chickenService.deleteList(deadChickenList);
            System.out.println("deleted: " + deadChickenList);
        }
    }

    public void oviposition() {
        List<Chicken> chickenList = chickenService.findAll();
        Random random = new Random();
        for (Chicken chicken : chickenList) {
            boolean layEgg = random.nextBoolean();
            if (layEgg) {
                Egg newEgg = new Egg(LocalDate.now(), farmService.getFarmByLoggedUser(), productService.findById(1));
                eggService.save(newEgg);
                productService.updateStock("oviposition", 1, 1, 1);
                System.out.println("new egg: " + newEgg);
            }
        }
    }


    // main data board
    @GetMapping("/main")
    public String indexData(Model theModel) {

        // Farm
        String farmName = farmService.getFarmByLoggedUser().getFarmName();
        theModel.addAttribute("farmName", farmName);

   //     LocalDate fakeDate = farmService.getFarmByLoggedUser().getFakeDate();
     //   theModel.addAttribute("fakeDate", fakeDate);
       // System.out.println("fake date: " + fakeDate);

        // Balances
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


        // Eggs
        int eggsCount = productService.findById(1).getProductStock();
        theModel.addAttribute("eggsCount", eggsCount);

        int eggSales = transactionRestService.eggSalesCount();
        theModel.addAttribute("eggSales", eggSales);

        int eggPurchases = transactionRestService.eggPurchasesCount();
        theModel.addAttribute("eggPurchases", eggPurchases);

        int eggsCapacity = parameterService.findById(1).getParameterValue();
        theModel.addAttribute("eggsCapacity", eggsCapacity);

        theModel.addAttribute("hatchedEggs", hatchedEggs);


        // Chickens
        int chickensCount = productService.findById(2).getProductStock();
        theModel.addAttribute("chickensCount", chickensCount);

        int chickenSales = transactionRestService.chickenSalesCount();
        theModel.addAttribute("chickenSales", chickenSales);

        int chickenPurchases = transactionRestService.chickenPurchasesCount();
        theModel.addAttribute("chickenPurchases", chickenPurchases);

        int chickenCapacity = parameterService.findById(2).getParameterValue();
        theModel.addAttribute("chickenCapacity", chickenCapacity);

        theModel.addAttribute("deadChicken", deadChicken);

        return "main";
    }




}