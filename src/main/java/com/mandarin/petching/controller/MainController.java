package com.mandarin.petching.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Optional;

@Controller
public class MainController {

    @GetMapping(value = "/")
    public String main(){

        return "index";
    }

}