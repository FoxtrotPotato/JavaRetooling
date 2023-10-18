package com.foxtrotpotato.chickentest.repository;

import com.foxtrotpotato.chickentest.entity.Log;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LogRepository extends JpaRepository<Log, Integer> {
    List<Log> findAllByOrderByLogTimestamp();

}



