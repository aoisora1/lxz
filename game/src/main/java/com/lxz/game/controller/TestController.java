package com.lxz.game.controller;

import com.lxz.game.service.TestTableService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private TestTableService testTableService;

    @GetMapping("/{id}")
    public String test(@PathVariable("id") int id) {
        testTableService.queryById(id);
        return "test";
    }
}
