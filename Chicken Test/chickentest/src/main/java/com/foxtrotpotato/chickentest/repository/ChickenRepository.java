package com.foxtrotpotato.chickentest.repository;

import com.foxtrotpotato.chickentest.entity.Chicken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChickenRepository extends JpaRepository<Chicken, Integer> {
    public List<Chicken> findAllByOrderByChickenBirthDayAsc();
}
