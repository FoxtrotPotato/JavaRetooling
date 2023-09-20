package com.foxtrotpotato.chickentest.service;

import com.foxtrotpotato.chickentest.entity.Egg;
import com.foxtrotpotato.chickentest.entity.Farm;
import com.foxtrotpotato.chickentest.entity.Product;

import java.util.List;

public interface EggService {
    List<Egg> findAll();

    Egg findById(int theId);

    void save(Egg theEgg);

    void deleteById(int theId);

    int calculateEggAgeInDays(int theId);

    void createDeleteEggs(String balanceType, int quantity, Product product, Farm farm);
}
