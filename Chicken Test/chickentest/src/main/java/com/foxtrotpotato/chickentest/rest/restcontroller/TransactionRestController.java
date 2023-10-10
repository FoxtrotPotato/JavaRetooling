package com.foxtrotpotato.chickentest.rest.restcontroller;

import com.foxtrotpotato.chickentest.entity.*;
import com.foxtrotpotato.chickentest.rest.restservice.TransactionRestService;
import com.foxtrotpotato.chickentest.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("api/transactions")
public class TransactionRestController {

    private final TransactionRestService transactionRestService;
    private final TransactionService transactionService;
    private final ProductService productService;
    private final BalanceService balanceService;
    private final EggService eggService;
    private final ChickenService chickenService;
    private final TransactionDetailService transactionDetailService;
    private final ParameterService parameterService;
    private final FarmService farmService;

    @Autowired
    public TransactionRestController(TransactionRestService transactionRestService,
                                     TransactionService transactionService,
                                     TransactionDetailService transactionDetailService,
                                     ProductService productService,
                                     BalanceService balanceService,
                                     EggService eggService,
                                     ChickenService chickenService,
                                     ParameterService parameterService,
                                     FarmService farmService) {
        this.transactionRestService = transactionRestService;
        this.transactionService = transactionService;
        this.transactionDetailService = transactionDetailService;
        this.productService = productService;
        this.balanceService = balanceService;
        this.chickenService = chickenService;
        this.eggService = eggService;
        this.parameterService = parameterService;
        this.farmService = farmService;
    }

    @GetMapping("/getPrice")
    public Double getProductPrice(@RequestParam int productId) {
        return productService.getProductPrice(productId);
    }

    @PostMapping("/save")
    public ResponseEntity<String> saveTransaction(@RequestBody Map<String, Object> json) {return transactionRestService.saveTransaction(json);}

}



