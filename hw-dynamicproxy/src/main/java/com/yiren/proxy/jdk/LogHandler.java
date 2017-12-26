package com.yiren.proxy.jdk;

import com.yiren.proxy.ILog;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author wanghao
 * create 2017-12-23 20:44
 **/
public class LogHandler implements InvocationHandler {

    private ILog target;

    public LogHandler(ILog log) {
        this.target = log;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String name = method.getName();
        before(name);
        Object obj = method.invoke(target, null);
        after(name);
        return obj;
    }

    public void before(String methodName) {
        System.out.println(methodName + " before");
    }

    public void after(String methodName) {
        System.out.println(methodName + "after");
    }

}
