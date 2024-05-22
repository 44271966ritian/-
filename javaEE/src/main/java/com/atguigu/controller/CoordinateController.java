package com.atguigu.controller;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api")
@RestController
public class CoordinateController {

    @PostMapping("search")
    public String cx(){
        return "111";
    }

}
