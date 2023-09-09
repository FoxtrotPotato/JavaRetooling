package com.foxtrotpotato.chickentest.dao;

import com.foxtrotpotato.chickentest.entity.Balance;
import com.foxtrotpotato.chickentest.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BalanceRepository extends JpaRepository<Balance, Integer> {
    public List<Balance> findAllByOrderByBalanceIdDesc();

}
