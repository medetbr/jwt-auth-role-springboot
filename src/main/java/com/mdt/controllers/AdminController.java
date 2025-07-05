package com.mdt.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api")
@RestController
public class AdminController {
    @GetMapping("/admin")
    public String getAdmin(){
        return "admin işlemi başarılı";
    }
}
