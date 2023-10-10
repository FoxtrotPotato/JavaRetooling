package com.foxtrotpotato.chickentest.controller;

import com.foxtrotpotato.chickentest.entity.User;
import com.foxtrotpotato.chickentest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService theUserService) {
        userService = theUserService;
    }

    @GetMapping("/list")
    public String listUser(Model theModel) {

        List<User> theUsers = userService.findAll();

        // add to the spring model
        theModel.addAttribute("users", theUsers);

        return "users/list-users";
    }

}
