package com.yiren.springmvc.service.impl;

import com.yiren.springmvc.annotation.Qualifier;
import com.yiren.springmvc.annotation.Service;
import com.yiren.springmvc.dao.UserDao;
import com.yiren.springmvc.service.UserService;

/**
 * @author wanghao
 * create 2017-12-23 19:09
 **/
@Service("userService")
public class UserServiceImpl implements UserService {

    @Qualifier("userDao")
    private UserDao userDao;
    @Override
    public void insert() {
        System.out.println("UserServiceImpl.insert() start");
        userDao.insert();
        System.out.println("UserServiceImpl.insert() end");
    }
}
