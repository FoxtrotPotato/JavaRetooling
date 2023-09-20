package com.foxtrotpotato.chickentest.dao;

import com.foxtrotpotato.chickentest.entity.Parameter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ParameterRepository extends JpaRepository<Parameter, Integer> {
    List<Parameter> findAllByOrderByParameterIdAsc();

}
