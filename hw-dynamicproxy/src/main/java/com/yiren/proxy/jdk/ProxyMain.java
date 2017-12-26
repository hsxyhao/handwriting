package com.yiren.proxy.jdk;

import com.yiren.proxy.ConsoleLog;
import com.yiren.proxy.ILog;
import sun.misc.ProxyGenerator;

import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Proxy;

/**
 * @author wanghao
 * create 2017-12-23 21:00
 **/
public class ProxyMain {
    public static void main(String[] args) {
        ILog log = new ConsoleLog();
        LogHandler logHandler = new LogHandler(log);
        ILog logProxy = (ILog) Proxy.newProxyInstance(log.getClass().getClassLoader(),
                new Class[]{ILog.class},
                logHandler);
        logProxy.info();
        outputProxyClassFile(ILog.class);
    }

    private static void outputProxyClassFile(Class c) {
        byte[] data = ProxyGenerator.generateProxyClass("$Proxy0", new Class[]{c});
        try {
            FileOutputStream fileOutputStream = new FileOutputStream("$Proxy0.class");
            fileOutputStream.write(data);
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
