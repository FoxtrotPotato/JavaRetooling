package com.foxtrotpotato.chickentest.controller;

import com.foxtrotpotato.chickentest.entity.Chicken;
import com.foxtrotpotato.chickentest.service.ChickenService;
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
@RequestMapping("/chickens")
public class ChickenController {

    private final ChickenService chickenService;
    private final GlobalData globalData;
    private final ProductService productService;
    private final FarmService farmService;


    @Autowired
    public ChickenController(ChickenService chickenService, GlobalData globalData, ProductService productService, FarmService farmService) {
        this.chickenService = chickenService;
        this.globalData = globalData;
        this.productService = productService;
        this.farmService = farmService;
    }

    @PreAuthorize("hasRole('EMPLOYEE')")
    @GetMapping(value = {"/", "/list"})
    public String listChicken(Model theModel) {
        List<Chicken> theChickens = chickenService.findAll();
        List<String> theStringChickens = new ArrayList<>();
        LocalDate currentDate = globalData.getCurrentDate();

        for (Chicken tempChicken : theChickens) {
            int ageInDays = chickenService.calculateChickenAgeInDays(tempChicken.getChickenId(), currentDate);
            tempChicken.setAgeInDays(ageInDays);

            String stringChicken = tempChicken.getChickenId() + ", "
                    + tempChicken.getChickenBirthDay().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + ", "
                    + tempChicken.getAgeInDays();

            theStringChickens.add(stringChicken);
        }

        theModel.addAttribute("chickens", theStringChickens);

        return "chickens/list-chickens";
    }

    @PreAuthorize("hasRole('MANAGER')")
    @GetMapping("/showUpdateChickenForm")
    public String showFormForUpdate(@RequestParam("chickenId") int theId, Model theModel) {
        Chicken theChicken = chickenService.findById(theId);
        theModel.addAttribute("chicken", theChicken);

        return "chickens/chicken-form";
    }

    @PostMapping("/save")
    public String saveChicken(@ModelAttribute("chicken") Chicken theChicken) {
        theChicken.setProduct(productService.findById(theChicken.getProduct().getProductId()));
        theChicken.setFarm(farmService.getFarmByLoggedUser());

        chickenService.save(theChicken);
        return "redirect:/chickens/list";
    }

}
