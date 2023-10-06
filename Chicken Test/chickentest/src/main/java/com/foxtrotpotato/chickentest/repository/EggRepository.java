package com.foxtrotpotato.chickentest.repository;

import com.foxtrotpotato.chickentest.entity.Egg;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EggRepository extends JpaRepository<Egg, Integer> {
    public List<Egg> findAllByOrderByEggBirthDayAsc();
}
