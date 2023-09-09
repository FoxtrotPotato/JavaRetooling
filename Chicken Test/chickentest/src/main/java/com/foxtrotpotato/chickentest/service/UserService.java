package com.foxtrotpotato.chickentest.service;

import com.foxtrotpotato.chickentest.entity.User;

import java.util.List;

public interface UserService {

    List<User> findAll();

    User findById(int theId);

    void save(User theUser);

    void deleteById(int theId);

}
