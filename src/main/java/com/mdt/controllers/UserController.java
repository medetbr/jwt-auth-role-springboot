package com.mdt.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api")
@RestController
public class UserController {
    @GetMapping("/user")
    public String getUser(){
        return "user işlemi başarılı";
    }
}
