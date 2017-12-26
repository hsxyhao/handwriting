package com.yiren.proxy.my;

import com.yiren.proxy.ConsoleLog;
import com.yiren.proxy.ILog;

/**
 * @author wanghao
 * create 2017-12-25 11:58
 **/
public class Main {
    public static void main(String[] args) {
        ILog log = new ConsoleLog();
        MyHandler myHandler = new MyHandler(log);
        ILog iLog = (ILog) MyProxy.newProxyInstance(new MyClassLoader("./", "com.yiren.proxy.my"), ILog.class, myHandler);
        System.out.println(iLog.getClass().getName());
        iLog.info();
    }
}
