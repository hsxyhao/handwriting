package com.yiren.service;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.Socket;

/**
 * @author wanghao
 * create 2017-12-24 17:50
 **/
public class RpcInvocationHandler implements InvocationHandler {
    private Class<?> target;

    RpcInvocationHandler(Class<?> clazz) {
        this.target = clazz;
    }

    @SuppressWarnings("unchecked")
    <T> T newInstance() {
        return (T) Proxy.newProxyInstance(target.getClassLoader(), new Class[]{target}, this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String apiClassName = target.getName();
        String methodName = method.getName();
        Socket socket = new Socket("127.0.0.1", 8888);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        objectOutputStream.writeUTF(apiClassName);
        objectOutputStream.writeUTF(methodName);
        objectOutputStream.writeObject(method.getParameterTypes());
        objectOutputStream.writeObject(args);
        objectOutputStream.flush();

        ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
        Object o = objectInputStream.readObject();
        objectOutputStream.close();
        objectInputStream.close();
        socket.close();
        return o;
    }
}
