package com.example.selfspringcore.controller;

import com.example.selfspringcore.annotation.CustomAnnotation.Autowired;
import com.example.selfspringcore.annotation.CustomAnnotation.Controller;


@Controller
public class ControllerB {
    private ControllerA controllerA;

    @Autowired
    public ControllerB(ControllerA controllerA) {
        this.controllerA = controllerA;
    }
}
