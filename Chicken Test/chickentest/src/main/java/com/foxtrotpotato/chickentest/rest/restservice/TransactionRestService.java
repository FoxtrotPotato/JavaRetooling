package com.foxtrotpotato.chickentest.rest.restservice;

import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface TransactionRestService {

    ResponseEntity<String> saveTransaction(Map<String, Object> json);

    int eggSalesCount();

    int eggPurchasesCount();

    int chickenSalesCount();

    int chickenPurchasesCount();

}
