package com.foxtrotpotato.chickentest.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class RedirectController {
    @GetMapping(value={"/", "/index"})
    public RedirectView redirectToIndex(){
        return new RedirectView("/index.html");
    }
}
