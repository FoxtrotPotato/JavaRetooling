package com.foxtrotpotato.chickentest.controller;

import com.foxtrotpotato.chickentest.entity.Chicken;
import com.foxtrotpotato.chickentest.entity.Egg;
import com.foxtrotpotato.chickentest.rest.restservice.TransactionRestService;
import com.foxtrotpotato.chickentest.service.*;
import com.foxtrotpotato.chickentest.util.GlobalData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;

@Controller
public class IndexController {
    private final TransactionRestService transactionRestService;
    private final ProductService productService;
    private final BalanceService balanceService;
    private final EggService eggService;
    private final ChickenService chickenService;
    private final ParameterService parameterService;
    private final FarmService farmService;
    private final GlobalData globalData;

    @Autowired
    public IndexController(TransactionRestService transactionRestService,
                           ProductService productService,
                           BalanceService balanceService,
                           EggService eggService,
                           ChickenService chickenService,
                           ParameterService parameterService,
                           FarmService farmService,
                           GlobalData globalData) {
        this.transactionRestService = transactionRestService;
        this.productService = productService;
        this.balanceService = balanceService;
        this.chickenService = chickenService;
        this.eggService = eggService;
        this.parameterService = parameterService;
        this.farmService = farmService;
        this.globalData = globalData;
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

    @PostMapping("/advanceDays")
    public String advanceDays(@RequestParam("daysToAdd") int daysToAdd) {
        LocalDate tempDate = GlobalData.currentDate.plusDays(daysToAdd);
        globalData.setCurrentDate(tempDate);
        LocalDateTime tempDateTime = GlobalData.currentDateTime.plusDays(daysToAdd);
        globalData.setCurrentDateTime(tempDateTime);

        hatchEggs();
        killChicken();
        oviposition();

        return "index";
    }

    public void hatchEggs() {
        List<Egg> hatchedEggsList = eggService.checkBirthdays(eggLifeSpan, GlobalData.currentDate);
        int hatchedEggsQty = (hatchedEggsList != null) ? hatchedEggsList.size() : 0;
        int tempChickenCapacity = parameterService.findById(2).getParameterValue();

        if (hatchedEggsQty > 0) {
            globalData.setHatchedEggs(globalData.getHatchedEggs() + hatchedEggsQty);
            productService.updateStock("HATCH", 1, hatchedEggsQty, parameterService.findById(1).getParameterValue());
            eggService.deleteList(hatchedEggsList);

            for (int i = 0; i < hatchedEggsQty; i++) {
                System.out.println("hatch eggs temp stock=" + productService.findById(2).getProductStock());
                System.out.println("hatch eggs temp capacity=" + tempChickenCapacity);

                if (productService.findById(2).getProductStock() < tempChickenCapacity) {
                    Chicken newChicken = new Chicken(globalData.getCurrentDate(), farmService.getFarmByLoggedUser(), productService.findById(2));
                    chickenService.save(newChicken);
                }
                productService.updateStock("birth", 2, 1, tempChickenCapacity);
            }
        }
    }

    public void killChicken() {
        List<Chicken> deadChickenList = chickenService.checkBirthdays(chickenLifeSpan, GlobalData.currentDate);
        System.out.println("deadChickenList:" + deadChickenList);
        int deadChickenQty = (deadChickenList != null) ? deadChickenList.size() : 0;

        if (deadChickenQty > 0) {
            globalData.setDeadChicken(globalData.getDeadChicken() + deadChickenQty);
            productService.updateStock("DEATH", 2, deadChickenQty, parameterService.findById(2).getParameterValue());
            chickenService.deleteList(deadChickenList);
            System.out.println("deleted: " + deadChickenList);
        }
    }

    public void oviposition() {
        List<Chicken> chickenList = chickenService.findAll();
        Random random = new Random();
        int tempCapacity = parameterService.findById(1).getParameterValue();

        for (Chicken chicken : chickenList) {
            boolean layEgg = random.nextBoolean();
            if (layEgg) {
                if (productService.findById(1).getProductStock() < tempCapacity) {
                    Egg newEgg = new Egg(globalData.getCurrentDate(), farmService.getFarmByLoggedUser(), productService.findById(1));
                    eggService.save(newEgg);
                    System.out.println("new egg: " + newEgg);
                }
                productService.updateStock("oviposition", 1, 1, parameterService.findById(1).getParameterValue());
            }
        }
    }


    // main data board
    @PreAuthorize("hasAnyRole('EMPLOYEE', 'MANAGER', 'ADMIN')")
    @GetMapping("/main")
    public String indexData(Model theModel) {
        DecimalFormat df = new DecimalFormat("0.00");

        DateTimeFormatter pattern = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String currentDate = GlobalData.currentDate.format(pattern);
        theModel.addAttribute("currentDate", currentDate);
        System.out.println("fake date: " + currentDate);

        // Farm
        String farmName = farmService.getFarmByLoggedUser().getFarmName();
        theModel.addAttribute("farmName", farmName);

        // Balances
        Double lastBalance = balanceService.getLastBalance();
        theModel.addAttribute("lastBalance", df.format(lastBalance));

        Double salesSum = balanceService.sumSalesBalances();
        theModel.addAttribute("salesSum", df.format(salesSum));

        Double purchasesSum = balanceService.sumPurchasesBalances();
        theModel.addAttribute("purchasesSum", df.format(purchasesSum));

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

        theModel.addAttribute("hatchedEggs", globalData.getHatchedEggs());
        theModel.addAttribute("discardedEggs", globalData.getDiscardedEggs());

        // Chickens
        int chickensCount = productService.findById(2).getProductStock();
        theModel.addAttribute("chickensCount", chickensCount);

        int chickenSales = transactionRestService.chickenSalesCount();
        theModel.addAttribute("chickenSales", chickenSales);

        int chickenPurchases = transactionRestService.chickenPurchasesCount();
        theModel.addAttribute("chickenPurchases", chickenPurchases);

        int chickenCapacity = parameterService.findById(2).getParameterValue();
        theModel.addAttribute("chickenCapacity", chickenCapacity);

        theModel.addAttribute("deadChicken", globalData.getDeadChicken());
        theModel.addAttribute("discardedChicken", globalData.getDiscardedChicken());

        return "main";
    }


}
