package com.foxtrotpotato.chickentest.service;

import com.foxtrotpotato.chickentest.entity.Chicken;

import java.util.List;

public interface ChickenService {

    List<Chicken> findAll();

    Chicken findById(int theId);

    void save(Chicken theChicken);

    void deleteById(int theId);

    int calculateChickenAgeInDays(int theId);

}
