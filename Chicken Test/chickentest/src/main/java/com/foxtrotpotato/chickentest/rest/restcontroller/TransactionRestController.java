package com.foxtrotpotato.chickentest.rest.restcontroller;

import com.foxtrotpotato.chickentest.rest.restservice.TransactionRestService;
import com.foxtrotpotato.chickentest.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("api/transactions")
public class TransactionRestController {
    private final TransactionRestService transactionRestService;
    private final ProductService productService;

    @Autowired
    public TransactionRestController(TransactionRestService transactionRestService, ProductService productService) {
        this.transactionRestService = transactionRestService;
        this.productService = productService;
    }

    @GetMapping("/getPrice")
    public Double getProductPrice(@RequestParam int productId) {
        return productService.getProductPrice(productId);
    }

    @PostMapping("/save")
    public ResponseEntity<String> saveTransaction(@RequestBody Map<String, Object> json) {
        return transactionRestService.saveTransaction(json);
    }

}



