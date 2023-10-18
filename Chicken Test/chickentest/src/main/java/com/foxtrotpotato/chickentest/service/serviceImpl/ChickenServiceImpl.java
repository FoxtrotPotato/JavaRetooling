package com.foxtrotpotato.chickentest.service.serviceImpl;

import com.foxtrotpotato.chickentest.entity.Chicken;
import com.foxtrotpotato.chickentest.entity.Farm;
import com.foxtrotpotato.chickentest.entity.Product;
import com.foxtrotpotato.chickentest.repository.ChickenRepository;
import com.foxtrotpotato.chickentest.service.ChickenService;
import com.foxtrotpotato.chickentest.util.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ChickenServiceImpl implements ChickenService {
    private final ChickenRepository chickenRepository;

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
            throw new ObjectNotFoundException("Did not find Chicken id - " + theId);
        }

        return theChicken;
    }

    @Override
    public int calculateChickenAgeInDays(int theId, LocalDate currentDate) {
        Optional<Chicken> optionalChicken = chickenRepository.findById(theId);
        Chicken tempChicken;
        int chickenAge;

        if (optionalChicken.isPresent()) {
            tempChicken = optionalChicken.get();
            LocalDate tempBirthDay = tempChicken.getChickenBirthDay();
            chickenAge = (int) ChronoUnit.DAYS.between(tempBirthDay, currentDate);
        } else {
            throw new RuntimeException("Did not find Chicken id - " + theId);
        }

        return chickenAge;
    }

    @Override
    @Transactional
    public void save(Chicken theChicken) {
        chickenRepository.save(theChicken);
    }

    @Override
    @Transactional
    public void deleteById(int theId) {
        chickenRepository.deleteById(theId);
    }

    @Override
    @Transactional
    public void deleteList(List<Chicken> chickenList) {
        for (Chicken chicken : chickenList) {
            deleteById(chicken.getChickenId());
        }
    }

    @Override
    @Transactional
    public void createDeleteChickens(String balanceType, int quantity, Product product, Farm farm, LocalDate currentDate, int maxCapacity) {
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
                if (product.getProductStock() < maxCapacity) {
                    Chicken theChicken = new Chicken();
                    theChicken.setChickenBirthDay(currentDate);
                    theChicken.setProduct(product);
                    theChicken.setFarm(farm);
                    save(theChicken);
                }
            }
        }
    }

    public List<Chicken> checkBirthdays(int chickenLifeSpan, LocalDate currentDate) {
        List<Chicken> tempChickensList = findAll();
        return tempChickensList.stream().filter(chicken ->
                (int) ChronoUnit.DAYS.between(chicken.getChickenBirthDay(), currentDate) >= chickenLifeSpan).collect(Collectors.toList());
    }


}
