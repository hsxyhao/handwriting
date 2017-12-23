package com.yiren.springmvc.controller;

import com.yiren.springmvc.annotation.Controller;
import com.yiren.springmvc.annotation.Qualifier;
import com.yiren.springmvc.annotation.RequestMapping;
import com.yiren.springmvc.service.UserService;

/**
 * @author wanghao
 * create 2017-12-23 19:06
 **/
@Controller("userController")
@RequestMapping("/users")
public class UserController {

    @Qualifier("userService")
    private UserService userService;

    @RequestMapping("/insert")
    public void insert(){
        userService.insert();
    }

}
