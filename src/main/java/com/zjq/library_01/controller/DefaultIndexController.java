package com.zjq.library_01.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DefaultIndexController {
    @GetMapping("/")
    public String defaultIndex(){
        return "login";
    }
}
