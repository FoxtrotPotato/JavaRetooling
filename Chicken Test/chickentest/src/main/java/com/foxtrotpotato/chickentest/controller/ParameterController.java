package com.foxtrotpotato.chickentest.controller;

import com.foxtrotpotato.chickentest.entity.Parameter;
import com.foxtrotpotato.chickentest.service.ParameterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/parameters")
public class ParameterController {

    private ParameterService parameterService;

    @Autowired
    public ParameterController(ParameterService theParameterService) {
        parameterService = theParameterService;
    }

    @GetMapping("/list")
    public String listParameter(Model theModel) {
        List<Parameter> theParameters = parameterService.findAll();
        theModel.addAttribute("parameters", theParameters);
        return "parameters/list-parameters";
    }

    @GetMapping("/showAddParameterForm")
    public String showFormForAdd(Model theModel) {

        Parameter theParameter = new Parameter();
        theModel.addAttribute("parameter", theParameter);

        return "parameters/parameter-form";
    }

    @GetMapping("/showUpdateParameterForm")
    public String showFormForUpdate(@RequestParam("parameterId") int theId, Model theModel) {

        Parameter theParameter = parameterService.findById(theId);
        theModel.addAttribute("parameter", theParameter);

        return "parameters/parameter-form";
    }

    @PostMapping("/save")
    public String saveParameter(@ModelAttribute("parameter") Parameter theParameter) {
        parameterService.save(theParameter);
        return "redirect:/parameters/list";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("parameterId") int theId) {
        parameterService.deleteById(theId);
        return "redirect:/parameters/list";
    }
}
