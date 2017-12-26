package com.yiren.proxy.my;

import sun.misc.ProxyGenerator;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wanghao
 * create 2017-12-23 21:22
 **/
public class MyClassLoader extends ClassLoader {
    private File file;
    private String proxyClassPackage;

    public MyClassLoader(String path, String proxyClassPackage) {
        this.file = new File(path);
        this.proxyClassPackage = proxyClassPackage;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        if (file != null) {
            File classFile = new File(file, name + ".class");
            if (classFile.exists()) {
                try {
                    byte[] data = copyFile(classFile);
                    return defineClass(proxyClassPackage + "." + name, data, 0, data.length);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return super.findClass(name);
    }

    private byte[] copyFile(File file) throws Exception {
        FileInputStream fis = new FileInputStream(file);
        byte[] buffer = new byte[fis.available()];
        int len = fis.read(buffer, 0, buffer.length);
        if (len == 0) {
            throw new Exception("空文件");
        }
        return buffer;
    }

    public File getFile() {
        return file;
    }

    public String getProxyClassPackage() {
        return proxyClassPackage;
    }
}
