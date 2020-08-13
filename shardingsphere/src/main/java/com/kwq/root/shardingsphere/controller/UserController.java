package com.kwq.root.shardingsphere.controller;

import com.kwq.root.shardingsphere.service.UserService;
import io.swagger.annotations.Api;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @auther : kongweiqiang
 * @DATE : 2020/8/13
 * @DESC :
 */
@Api("用户中心")
@Log4j2
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

}
