package com.yiren.proxy;

/**
 * @author wanghao
 * create 2017-12-23 20:43
 **/
public class ConsoleLog implements ILog {
    @Override
    public void info() {
        System.out.println("ConsoleLog.info()");
    }
}
