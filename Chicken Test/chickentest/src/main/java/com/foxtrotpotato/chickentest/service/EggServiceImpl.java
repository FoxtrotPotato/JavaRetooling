package com.foxtrotpotato.chickentest.service;

import com.foxtrotpotato.chickentest.dao.EggRepository;
import com.foxtrotpotato.chickentest.entity.Egg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

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
    public int calculateEggAgeInDays(int theId) {
        Optional<Egg> optionalEgg = eggRepository.findById(theId);
        Egg tempEgg;
        int eggAge;

        if (optionalEgg.isPresent()) {
            tempEgg = optionalEgg.get();
            LocalDate tempBirthDay = tempEgg.getEggBirthDay();
            LocalDate currentDate = LocalDate.now();
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


}
