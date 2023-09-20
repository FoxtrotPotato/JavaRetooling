package com.foxtrotpotato.chickentest.service;

import com.foxtrotpotato.chickentest.dao.ChickenRepository;
import com.foxtrotpotato.chickentest.entity.Chicken;
import com.foxtrotpotato.chickentest.entity.Egg;
import com.foxtrotpotato.chickentest.entity.Farm;
import com.foxtrotpotato.chickentest.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
public class ChickenServiceImpl implements ChickenService {


    private ChickenRepository chickenRepository;

    @Autowired
    public ChickenServiceImpl(ChickenRepository theChickenRepository) {
        chickenRepository = theChickenRepository;
    }

    @Override
    public List<Chicken> findAll() {
        return chickenRepository.findAllByOrderByChickenBirthDayAsc();
    }

    @Override
    public Chicken findById(int theId) {
        Optional<Chicken> result = chickenRepository.findById(theId);

        Chicken theChicken;

        if (result.isPresent()) {
            theChicken = result.get();
        } else {
            throw new RuntimeException("Did not find Chicken id - " + theId);
        }

        return theChicken;
    }

    @Override
    public int calculateChickenAgeInDays(int theId) {
        Optional<Chicken> optionalChicken = chickenRepository.findById(theId);
        Chicken tempChicken;
        int chickenAge;

        if (optionalChicken.isPresent()) {
            tempChicken = optionalChicken.get();
            LocalDate tempBirthDay = tempChicken.getChickenBirthDay();
            LocalDate currentDate = LocalDate.now();
            chickenAge = (int) ChronoUnit.DAYS.between(tempBirthDay, currentDate);
        } else {
            throw new RuntimeException("Did not find Chicken id - " + theId);
        }

        return chickenAge;
    }

    @Override
    public void save(Chicken theChicken) {
        chickenRepository.save(theChicken);
    }

    @Override
    public void deleteById(int theId) {
        chickenRepository.deleteById(theId);
    }

    @Override
    public void createDeleteChickens(String balanceType, int quantity, Product product, Farm farm) {
        if (balanceType.equals("SALE")) {
            for (int i = 0; i < quantity; i++) {
                List<Chicken> chickensList = findAll();
                Chicken oldestChicken = chickensList.get(0);
                int oldestChickenId = oldestChicken.getChickenId();
                deleteById(oldestChickenId);
                System.out.println("deleted Chicken: " + oldestChickenId);
            }
        } else {
            for (int i = 0; i < quantity; i++) {
                Chicken theChicken = new Chicken();
                theChicken.setChickenBirthDay(LocalDate.now());
                theChicken.setProduct(product);
                theChicken.setFarm(farm);
                save(theChicken);
            }
        }
    }

}
