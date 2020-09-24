package com.kwq.root.elasticsearch.controller;

import com.kwq.root.elasticsearch.service.ESIndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @auther : kongweiqiang
 * @DATE : 2020/9/14
 * @DESC :
 */
@RestController(value = "index")
public class IndexController {

    @Autowired
    private ESIndexService esIndexService;

    @GetMapping
    public void addIndex(){

    }

}
