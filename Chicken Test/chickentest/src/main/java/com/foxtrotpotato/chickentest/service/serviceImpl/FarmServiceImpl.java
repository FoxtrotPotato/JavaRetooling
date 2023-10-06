package com.foxtrotpotato.chickentest.service.serviceImpl;

import com.foxtrotpotato.chickentest.repository.FarmRepository;
import com.foxtrotpotato.chickentest.entity.Farm;
import com.foxtrotpotato.chickentest.service.FarmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FarmServiceImpl implements FarmService {
    private final FarmRepository farmRepository;

    @Autowired
    public FarmServiceImpl(FarmRepository theFarmRepository) {
        farmRepository = theFarmRepository;
    }


    public List<Farm> findAll() {
        return farmRepository.findAll();
    }

    public Farm findById(int theId) {
        Optional<Farm> result = farmRepository.findById(theId);
        Farm theFarm;
        if (result.isPresent()) {
            theFarm = result.get();
        }
        else {
            throw new RuntimeException("Did not find Farm id - " + theId);
        }
        return theFarm;
    }

    @Override
    public Farm getFarmByLoggedUser() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authentication);
        Farm theFarm = null;

        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            System.out.println(username);
            theFarm = farmRepository.findByUsersUsername(username);

        } else {
            System.out.println("No authenticated user.");
        }

        return theFarm;
    }
}
