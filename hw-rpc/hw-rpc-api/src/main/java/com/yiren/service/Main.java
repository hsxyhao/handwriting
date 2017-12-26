package com.yiren.service;

import com.yiren.service.bean.Product;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.Socket;

/**
 * @author wanghao
 * create 2017-12-24 17:27
 **/
public class Main {
    public static void main(String[] args) {
        RpcInvocationHandler invocationHandler = new RpcInvocationHandler(IProductService.class);
        IProductService productService =invocationHandler.newInstance();
        Product product = productService.queryById(100L);
        System.out.println(product);
    }
}
