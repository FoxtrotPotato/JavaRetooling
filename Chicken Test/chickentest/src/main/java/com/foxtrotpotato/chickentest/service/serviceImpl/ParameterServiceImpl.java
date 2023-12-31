package com.foxtrotpotato.chickentest.service.serviceImpl;

import com.foxtrotpotato.chickentest.repository.ParameterRepository;
import com.foxtrotpotato.chickentest.entity.Parameter;
import com.foxtrotpotato.chickentest.service.ParameterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ParameterServiceImpl implements ParameterService {

    private final ParameterRepository parameterRepository;

    @Autowired
    public ParameterServiceImpl(ParameterRepository theParameterRepository) {
        parameterRepository = theParameterRepository;
    }

    @Override
    public List<Parameter> findAll() {
        return parameterRepository.findAllByOrderByParameterIdAsc();
    }

    @Override
    public Parameter findById(int theId) {
        Optional<Parameter> result = parameterRepository.findById(theId);
        Parameter theParameter;

        if (result.isPresent()) {
            theParameter = result.get();
        }
        else {
            throw new RuntimeException("Did not find Parameter id - " + theId);
        }

        return theParameter;
    }

    @Override
    @Transactional
    public void save(Parameter theParameter) {
        parameterRepository.save(theParameter);
    }

    @Override
    @Transactional
    public void deleteById(int theId) {
        parameterRepository.deleteById(theId);
    }


}
