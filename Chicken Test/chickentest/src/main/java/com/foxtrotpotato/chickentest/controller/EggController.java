package com.foxtrotpotato.chickentest.controller;

import com.foxtrotpotato.chickentest.entity.Egg;
import com.foxtrotpotato.chickentest.service.EggService;
import com.foxtrotpotato.chickentest.service.FarmService;
import com.foxtrotpotato.chickentest.service.ProductService;
import com.foxtrotpotato.chickentest.util.GlobalData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/eggs")
public class EggController {

    private final EggService eggService;
    private final GlobalData globalData;
    private final ProductService productService;
    private final FarmService farmService;


    @Autowired
    public EggController(EggService eggService, GlobalData globalData, ProductService productService, FarmService farmService) {
        this.eggService = eggService;
        this.globalData = globalData;
        this.productService = productService;
        this.farmService = farmService;
    }

    @PreAuthorize("hasRole('EMPLOYEE')")
    @GetMapping("/list")
    public String listEgg(Model theModel) {
        List<Egg> theEggs = eggService.findAll();
        List<String> theStringEggs = new ArrayList<>();
        LocalDate currentDate = globalData.getCurrentDate();

        for (Egg tempEgg : theEggs) {
            int ageInDays = eggService.calculateEggAgeInDays(tempEgg.getEggId(), currentDate);
            tempEgg.setAgeInDays(ageInDays);

            String stringEgg = tempEgg.getEggId() + ", "
                    + tempEgg.getEggBirthDay().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + ", "
                    + tempEgg.getAgeInDays();
            theStringEggs.add(stringEgg);
        }

        theModel.addAttribute("eggs", theStringEggs);

        return "eggs/list-eggs";
    }

    @PreAuthorize("hasRole('MANAGER')")
    @GetMapping("/showUpdateEggForm")
    public String showFormForUpdate(@RequestParam("eggId") int theId, Model theModel) {
        Egg theEgg = eggService.findById(theId);
        theModel.addAttribute("egg", theEgg);

        return "eggs/egg-form";
    }

    @PostMapping("/save")
    public String saveEgg(@ModelAttribute("egg") Egg theEgg) {
        theEgg.setProduct(productService.findById(theEgg.getProduct().getProductId()));
        theEgg.setFarm(farmService.getFarmByLoggedUser());

        eggService.save(theEgg);
        return "redirect:/eggs/list";
    }

}
