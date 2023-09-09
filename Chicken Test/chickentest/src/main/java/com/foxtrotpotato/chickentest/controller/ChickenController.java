package com.foxtrotpotato.chickentest.controller;

import com.foxtrotpotato.chickentest.entity.Chicken;
import com.foxtrotpotato.chickentest.entity.User;
import com.foxtrotpotato.chickentest.service.ChickenService;
import org.springframework.beans.factory.annotation.Autowired;
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

    private ChickenService chickenService;

    @Autowired
    public ChickenController(ChickenService theChickenService) {
        chickenService = theChickenService;
    }

    @GetMapping("/list")
    public String listChicken(Model theModel) {
        List<Chicken> theChickens = chickenService.findAll();
        List<String> theStringChickens = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");


        for (Chicken tempChicken : theChickens) {
            int ageInDays = chickenService.calculateChickenAgeInDays(tempChicken.getChickenId());
            tempChicken.setAgeInDays(ageInDays);

            String stringChicken = tempChicken.getChickenId() + ", "
                                   + tempChicken.getChickenBirthDay().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + ", "
                                   + tempChicken.getAgeInDays();

            theStringChickens.add(stringChicken);
        }

        theModel.addAttribute("chickens", theStringChickens);

        return "chickens/list-chickens";
    }

    @GetMapping("/showAddChickenForm")
    public String showFormForAdd(Model theModel) {

        Chicken theChicken = new Chicken();
        theChicken.setChickenBirthDay(LocalDate.now());
        theModel.addAttribute("chicken", theChicken);

        return "chickens/chicken-form";
    }

    @GetMapping("/showUpdateChickenForm")
    public String showFormForUpdate(@RequestParam("chickenId") int theId, Model theModel) {

        Chicken theChicken = chickenService.findById(theId);
        theModel.addAttribute("chicken", theChicken);

        return "chickens/chicken-form";
    }

    @PostMapping("/save")
    public String saveChicken(@ModelAttribute("chicken") Chicken theChicken) {
        chickenService.save(theChicken);
        return "redirect:/chickens/list";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("chickenId") int theId) {
        chickenService.deleteById(theId);
        return "redirect:/chickens/list";
    }

}
