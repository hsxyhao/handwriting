package com.yiren.springmvc.dao.impl;

import com.yiren.springmvc.annotation.Repository;
import com.yiren.springmvc.dao.UserDao;

/**
 * @author wanghao
 * create 2017-12-23 19:10
 **/
@Repository("userDao")
public class UserDaoImpl  implements UserDao{

    @Override
    public void insert() {
        System.out.println("execute UserDaoImpl.insert()");
    }
}
