package com.kwq.root.shardingsphere.controller;

import com.kwq.root.shardingsphere.model.User;
import com.kwq.root.shardingsphere.service.UserService;
import io.swagger.annotations.Api;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PutMapping("/add")
    public ModelMap add(@RequestBody User user){
        int result = userService.add(user);
        ModelMap modelMap = new ModelMap();
        modelMap.put("add",result);
        return modelMap;
    }

    @PostMapping("/update")
    public ModelMap update(@RequestBody User user){
        int result = userService.update(user);
        ModelMap modelMap = new ModelMap();
        modelMap.put("update",result);
        return modelMap;
    }

    @PutMapping("/delete")
    public ModelMap delete(User user){
        int result = userService.delete(user);
        ModelMap modelMap = new ModelMap();
        modelMap.put("delete",result);
        return modelMap;
    }

    @GetMapping("/find")
    public ModelMap find(User user){
        List<User> result = userService.find(user);
        ModelMap modelMap = new ModelMap();
        modelMap.put("find",result);
        return modelMap;
    }

    @GetMapping("/count")
    public ModelMap count(User user){
        Long result = userService.count(user);
        ModelMap modelMap = new ModelMap();
        modelMap.put("count",result);
        return modelMap;
    }
}
