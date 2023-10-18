package com.foxtrotpotato.chickentest.service.serviceImpl;

import com.foxtrotpotato.chickentest.entity.Log;
import com.foxtrotpotato.chickentest.repository.LogRepository;
import com.foxtrotpotato.chickentest.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LogServiceImpl implements LogService {
    private final LogRepository logRepository;

    @Autowired
    public LogServiceImpl(LogRepository theLogRepository) {
        logRepository = theLogRepository;
    }

    @Override
    public List<Log> findAll() {
        return logRepository.findAllByOrderByLogTimestamp();
    }

    @Override
    @Transactional
    public void save(Log theLog) {
        logRepository.save(theLog);
    }

}
