package com.foxtrotpotato.chickentest.service;

import com.foxtrotpotato.chickentest.entity.Log;

import java.util.List;

public interface LogService {
    List<Log> findAll();

    void save(Log theLog);
}
