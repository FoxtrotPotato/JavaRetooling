package com.foxtrotpotato.chickentest.service;

import com.foxtrotpotato.chickentest.dao.ParameterRepository;
import com.foxtrotpotato.chickentest.entity.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ParameterServiceImpl implements ParameterService {

    private ParameterRepository parameterRepository;

    @Autowired
    public ParameterServiceImpl(ParameterRepository theParameterRepository) {
        parameterRepository = theParameterRepository;
    }

    @Override
    public List<Parameter> findAll() {
        return parameterRepository.findAllByOrderByParameterIdDesc();
    }

    @Override
    public Parameter findById(int theId) {
        Optional<Parameter> result = parameterRepository.findById(theId);

        Parameter theParameter = null;

        if (result.isPresent()) {
            theParameter = result.get();
        }
        else {
            throw new RuntimeException("Did not find Parameter id - " + theId);
        }

        return theParameter;
    }

    @Override
    public void save(Parameter theParameter) {
        parameterRepository.save(theParameter);
    }

    @Override
    public void deleteById(int theId) {
        parameterRepository.deleteById(theId);
    }


}
