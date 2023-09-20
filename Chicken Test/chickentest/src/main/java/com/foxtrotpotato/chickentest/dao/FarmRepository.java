package com.foxtrotpotato.chickentest.dao;

import com.foxtrotpotato.chickentest.entity.Farm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FarmRepository extends JpaRepository<Farm, Integer> {
    Farm findByUsersUsername(String username);
}
