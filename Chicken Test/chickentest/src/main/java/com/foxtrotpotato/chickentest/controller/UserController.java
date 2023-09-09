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

    @GetMapping("/showAddUserForm")
    public String showFormForAdd(Model theModel) {

        User theUser = new User();
        theModel.addAttribute("user", theUser);

        return "users/user-form";
    }

    @GetMapping("/showUpdateUserForm")
    public String showFormForUpdate(@RequestParam("userId") int theId, Model theModel) {

        User theUser = userService.findById(theId);
        theModel.addAttribute("user", theUser);

        return "users/user-form";
    }

    @GetMapping("/showUpdatePasswordForm")
    public String showFormForPasswordUpdate(@RequestParam("userId") int theId, Model theModel) {

        User theUser = userService.findById(theId);
        theModel.addAttribute("user", theUser);

        return "users/password-form";
    }

    @PostMapping("/save")
    public String saveUser(@ModelAttribute("user") User theUser) {
        userService.save(theUser);
        return "redirect:/users/list";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("userId") int theId) {
        userService.deleteById(theId);
        return "redirect:/users/list";
    }
}
