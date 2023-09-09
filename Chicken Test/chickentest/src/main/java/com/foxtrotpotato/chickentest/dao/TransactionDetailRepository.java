package com.foxtrotpotato.chickentest.dao;

import com.foxtrotpotato.chickentest.entity.TransactionDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionDetailRepository extends JpaRepository<TransactionDetail, Integer> {
}
