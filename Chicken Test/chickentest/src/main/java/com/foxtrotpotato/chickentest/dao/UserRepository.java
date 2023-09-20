package com.foxtrotpotato.chickentest.dao;

import com.foxtrotpotato.chickentest.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    List<User> findAllByOrderByLastNameAsc();

    Optional<User> findByUsername(String theUsername);
}



