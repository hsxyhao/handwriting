package com.yiren;

import com.yiren.service.IProductService;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author wanghao
 * create 2017-12-24 17:15
 **/
public class Main {
    public static void main(String[] args){
        try {
            ServerSocket serverSocket = new ServerSocket(8888);
            while (true) {
                Socket socket = serverSocket.accept();
                ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
                String apiClassName = objectInputStream.readUTF();
                String methodName = objectInputStream.readUTF();
                Class[] parameterType = (Class[]) objectInputStream.readObject();
                Object[] args4Method = (Object[]) objectInputStream.readObject();
                Class<?> clazz = null;
                if (apiClassName.equals(IProductService.class.getName())) {
                    clazz = ProductService.class;
                }
                if (clazz == null) {
                    throw new Exception("异常");
                }
                Method method = clazz.getMethod(methodName,parameterType);
                method.setAccessible(true);
                Object invoker = method.invoke(clazz.newInstance(),args4Method);

                ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                objectOutputStream.writeObject(invoker);
                objectOutputStream.flush();
                objectInputStream.close();
                objectOutputStream.close();
                socket.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
