package com.foxtrotpotato.chickentest.rest;

import com.foxtrotpotato.chickentest.entity.Chicken;
import com.foxtrotpotato.chickentest.service.ChickenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/chickens")
public class ChickenRestController {

    private ChickenService chickenService;

    @Autowired
    public ChickenRestController(ChickenService theChickenService) {
        chickenService = theChickenService;
    }

    @PostMapping("/add")
    public ResponseEntity<Chicken> addChicken() {
        Chicken theChicken = new Chicken();
        LocalDate tempLocalDate = LocalDate.now();
        theChicken.setChickenBirthDay(tempLocalDate);
        chickenService.save(theChicken);
        return new ResponseEntity<>(theChicken, HttpStatus.CREATED);
    }



    @GetMapping("/updateChickenBirthday")
    public String updateChickenBirthday(@RequestParam("chickenId") int theId, Model theModel) {

        Chicken theChicken = chickenService.findById(theId);
        theModel.addAttribute("chicken", theChicken);

        return "chickens/chicken-form";
    }



    @GetMapping("/delete")
    public String delete(@RequestParam("chickenId") int theId) {
        chickenService.deleteById(theId);
        return "redirect:/chickens/list";
    }

}
