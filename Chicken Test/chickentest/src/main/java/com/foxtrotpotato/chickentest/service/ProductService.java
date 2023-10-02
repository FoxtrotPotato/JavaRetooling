package com.foxtrotpotato.chickentest.service;

import com.foxtrotpotato.chickentest.entity.Product;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProductService {
    List<Product> findAll();

    Product findById(int theId);

    void save(Product theUser);

    void deleteById(int theId);

    void deleteList(List<Product> products);

    ResponseEntity updateStock(String balanceType, int productId, int quantity, int maxCapacity);

    float getProductPrice(int productId);
}
