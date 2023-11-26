package com.example.chart.controller;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Author:XY
 * PACkAGE:com.example.chart.controller
 * Date:2023/11/24 15:17
 */
@Controller
public class Index {
    @GetMapping("/2")
    public String i(){
        return "index2";
    }
}
