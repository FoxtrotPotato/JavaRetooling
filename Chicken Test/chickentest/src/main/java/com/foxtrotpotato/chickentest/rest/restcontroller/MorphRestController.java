package com.foxtrotpotato.chickentest.rest.restcontroller;

import com.foxtrotpotato.chickentest.entity.Chicken;
import com.foxtrotpotato.chickentest.entity.Egg;
import com.foxtrotpotato.chickentest.service.ChickenService;
import com.foxtrotpotato.chickentest.service.EggService;
import com.foxtrotpotato.chickentest.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/api/morph")
public class MorphRestController {

    private final ChickenService chickenService;
    private final EggService eggService;
    private final ProductService productService;

    @Autowired
    public MorphRestController(ChickenService theChickenService,
                               EggService eggService,
                               ProductService productService) {
        chickenService = theChickenService;
        this.eggService = eggService;
        this.productService = productService;
    }



}
