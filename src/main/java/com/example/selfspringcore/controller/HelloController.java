package com.example.selfspringcore.controller;

import com.example.selfspringcore.annotation.CustomAnnotation;
import com.example.selfspringcore.annotation.GetMapping;
import com.example.selfspringcore.annotation.Model;

@CustomAnnotation.Controller
public class HelloController {

    @GetMapping("hello")
    public String hello(Model model) {
        model.addAttribute("data", "hello!!!");
        return "hello";
    }
}
