package com.foxtrotpotato.chickentest.controller;

import com.foxtrotpotato.chickentest.entity.Log;
import com.foxtrotpotato.chickentest.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;

@Controller
@RequestMapping("/logs")
public class LogController {

    private LogService logService;

    @Autowired
    public LogController(LogService theLogService) {
        logService = theLogService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/list")
    public String listLogs(Model theModel) {
        List<Log> theLogs = logService.findAll();
        theModel.addAttribute("logs", theLogs);
        return "logs/list-logs";
    }

}
