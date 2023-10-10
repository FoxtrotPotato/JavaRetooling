package com.foxtrotpotato.chickentest.service;

import com.foxtrotpotato.chickentest.entity.Chicken;
import com.foxtrotpotato.chickentest.entity.Farm;
import com.foxtrotpotato.chickentest.entity.Product;

import java.time.LocalDate;
import java.util.List;

public interface ChickenService {

    List<Chicken> findAll();

    Chicken findById(int theId);

    void save(Chicken theChicken);

    void deleteById(int theId);

    void deleteList(List<Chicken> chickenList);

    int calculateChickenAgeInDays(int theId, LocalDate currentDate);

    void createDeleteChickens(String balanceType, int quantity, Product product, Farm farm, LocalDate currentDate, int maxCapacity);

    List<Chicken> checkBirthdays(int chickenLifeSpan, LocalDate currentDate);


}
