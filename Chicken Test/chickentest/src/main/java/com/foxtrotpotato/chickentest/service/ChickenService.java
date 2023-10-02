package com.foxtrotpotato.chickentest.service;

import com.foxtrotpotato.chickentest.entity.Chicken;
import com.foxtrotpotato.chickentest.entity.Farm;
import com.foxtrotpotato.chickentest.entity.Product;

import java.util.List;

public interface ChickenService {

    List<Chicken> findAll();

    Chicken findById(int theId);

    void save(Chicken theChicken);

    void deleteById(int theId);

    void deleteList(List<Chicken> chickenList);

    int calculateChickenAgeInDays(int theId);

    void createDeleteChickens(String balanceType, int quantity, Product product, Farm farm);

    List<Chicken> checkBirthdays(int chickenLifeSpan);


}
