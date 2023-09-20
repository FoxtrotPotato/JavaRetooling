package com.foxtrotpotato.chickentest.service;

import com.foxtrotpotato.chickentest.dao.UserRepository;
import com.foxtrotpotato.chickentest.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {


    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository theUserRepository) {
        userRepository = theUserRepository;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAllByOrderByLastNameAsc();
    }

    @Override
    public User findById(int theId) {
        Optional<User> result = userRepository.findById(theId);
        User theUser = null;
        if (result.isPresent()) {
            theUser = result.get();
        }
        else {
            throw new RuntimeException("Did not find user id - " + theId);
        }
        return theUser;
    }

    @Override
    public User findByUserName(String theUsername) {
        Optional<User> result = userRepository.findByUsername(theUsername);
        User theUser = null;
        if (result.isPresent()) {
            theUser = result.get();
        }
        else {
            throw new RuntimeException("Did not find user id - " + theUsername);
        }
        return theUser;
    }

    @Override
    public void save(User theUser) {
        userRepository.save(theUser);
    }

    @Override
    public void deleteById(int theId) {
        userRepository.deleteById(theId);
    }




}
