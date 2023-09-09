package com.foxtrotpotato.chickentest.service;

import com.foxtrotpotato.chickentest.entity.Parameter;

import java.util.List;

public interface ParameterService {
    List<Parameter> findAll();

    Parameter findById(int theId);

    void save(Parameter theUser);

    void deleteById(int theId);
}
