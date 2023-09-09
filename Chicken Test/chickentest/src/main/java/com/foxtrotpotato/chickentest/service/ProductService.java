package com.foxtrotpotato.chickentest.service;

import com.foxtrotpotato.chickentest.entity.Product;

import java.util.List;

public interface ProductService {
    List<Product> findAll();

    Product findById(int theId);

    void save(Product theUser);

    void deleteById(int theId);
}