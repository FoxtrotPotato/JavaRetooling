package com.foxtrotpotato.chickentest.dao;

import com.foxtrotpotato.chickentest.entity.Balance;
import com.foxtrotpotato.chickentest.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    public List<Product> findAllByOrderByProductIdDesc();

}
