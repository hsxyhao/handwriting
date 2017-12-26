package com.yiren.proxy.my;

import com.yiren.proxy.ILog;

import java.lang.reflect.Method;

/**
 * @author wanghao
 * create 2017-12-23 21:18
 **/
public class MyHandler implements MyInvocationHandler {
    private ILog log;
    public MyHandler(ILog log) {
        this.log = log;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object... args) throws Throwable {
        String name = method.getName();
        before(name);
        method.invoke(log, null);
        after(name);
        return null;
    }

    public void before(String methodName) {
        System.out.println(methodName + " before");
    }

    public void after(String methodName) {
        System.out.println(methodName + "after");
    }

}
