package com.yiren.proxy.my;

import java.lang.reflect.Method;

/**
 * @author wanghao
 * create 2017-12-23 21:17
 **/
public interface MyInvocationHandler {
    /**
     * invoke
     * @param proxy 代理对象
     * @param method 反射方法对象
     * @param args 参数
     * @return 返回信息
     */
    Object invoke(Object proxy, Method method, Object... args) throws Throwable;

}
