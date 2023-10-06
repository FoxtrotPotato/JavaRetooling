package com.foxtrotpotato.chickentest.service.serviceImpl;

import com.foxtrotpotato.chickentest.repository.EggRepository;
import com.foxtrotpotato.chickentest.entity.Egg;
import com.foxtrotpotato.chickentest.entity.Farm;
import com.foxtrotpotato.chickentest.entity.Product;
import com.foxtrotpotato.chickentest.service.EggService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class EggServiceImpl implements EggService {


    private EggRepository eggRepository;

    @Autowired
    public EggServiceImpl(EggRepository theEggRepository) {
        eggRepository = theEggRepository;
    }

    @Override
    public List<Egg> findAll() {
        return eggRepository.findAllByOrderByEggBirthDayAsc();
    }

    @Override
    public Egg findById(int theId) {
        Optional<Egg> result = eggRepository.findById(theId);

        Egg theEgg;

        if (result.isPresent()) {
            theEgg = result.get();
        } else {
            throw new RuntimeException("Did not find Egg id - " + theId);
        }

        return theEgg;
    }

    @Override
    public int calculateEggAgeInDays(int theId, LocalDate currentDate) {
        Optional<Egg> optionalEgg = eggRepository.findById(theId);
        Egg tempEgg;
        int eggAge;

        if (optionalEgg.isPresent()) {
            tempEgg = optionalEgg.get();
            LocalDate tempBirthDay = tempEgg.getEggBirthDay();
            eggAge = (int) ChronoUnit.DAYS.between(tempBirthDay, currentDate);
        } else {
            throw new RuntimeException("Did not find Egg id - " + theId);
        }

        return eggAge;
    }

    @Override
    public void save(Egg theEgg) {
        eggRepository.save(theEgg);
    }

    @Override
    public void deleteById(int theId) {
        eggRepository.deleteById(theId);
    }

    @Override
    public void deleteList(List<Egg> eggsList){
        for (Egg egg : eggsList){
            deleteById(egg.getEggId());
        }
    }

    @Override
    public void createDeleteEggs(String balanceType, int quantity, Product product, Farm farm, LocalDate currentDate) {
        if (balanceType.equals("SALE")) {
            for (int i = 0; i < quantity; i++) {
                List<Egg> eggsList = findAll();
                Egg oldestEgg = eggsList.get(0);
                int oldestEggId = oldestEgg.getEggId();
                deleteById(oldestEggId);
                System.out.println("deleted egg: " + oldestEggId);
            }
        } else {
            for (int i = 0; i < quantity; i++) {
                Egg theEgg = new Egg();
                theEgg.setEggBirthDay(currentDate);
                theEgg.setProduct(product);
                theEgg.setFarm(farm);
                save(theEgg);
            }
        }
    }

    public List<Egg> checkBirthdays(int eggLifeSpan, LocalDate currentDate) {
        List<Egg> tempEggsList = findAll();
        return tempEggsList.stream().filter(egg ->
                (int) ChronoUnit.DAYS.between(egg.getEggBirthDay(), currentDate) >= eggLifeSpan).collect(Collectors.toList());
    }

}
