package com.foxtrotpotato.chickentest.repository;

import com.foxtrotpotato.chickentest.entity.Balance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BalanceRepository extends JpaRepository<Balance, Integer> {
    List<Balance> findAllByOrderByBalanceIdDesc();

}
