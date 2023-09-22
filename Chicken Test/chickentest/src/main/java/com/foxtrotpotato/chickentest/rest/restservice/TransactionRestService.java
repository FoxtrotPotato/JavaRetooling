package com.foxtrotpotato.chickentest.rest.restservice;

import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface TransactionRestService {

    public ResponseEntity<String> saveTransaction(Map<String, Object> json);

    int eggSalesCount();

    public int eggPurchasesCount();

    public int chickenSalesCount();

    public int chickenPurchasesCount();

}
