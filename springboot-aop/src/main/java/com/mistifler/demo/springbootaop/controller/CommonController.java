package com.mistifler.demo.springbootaop.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommonController {

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String test() {
        return "ok";
    }
}
