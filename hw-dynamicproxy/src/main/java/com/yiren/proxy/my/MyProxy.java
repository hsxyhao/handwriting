package com.yiren.proxy.my;

import org.springframework.util.FileCopyUtils;

import javax.tools.JavaCompiler;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author wanghao
 * create 2017-12-23 21:46
 **/
public class MyProxy {
    private static final String RT = "\r";

    public static Object newProxyInstance(MyClassLoader loader, Class<?> interfaces, MyInvocationHandler h) {
        if (h == null) {
            throw new NullPointerException("MyInvocationHandler is null");
        }

        Method[] methods = interfaces.getMethods();
        StringBuffer proxyClassFile = new StringBuffer();
        proxyClassFile.append("package ")
                .append(loader.getProxyClassPackage()).append(";").append(RT)
                .append("import java.lang.reflect.Method;")
                .append("public class $MyProxy0 implements ")
                .append(interfaces.getName()).append("{").append(RT)
                .append("MyInvocationHandler h;").append(RT)
                .append("public $MyProxy0(MyInvocationHandler h){")
                .append(RT).append("this.h = h;}").append(RT)
                .append(getMethodString(methods, interfaces)).append("}");
        String fileName = loader.getFile() + File.separator + "$MyProxy0.java";
        File myProxyFile = new File(fileName);
        try {
            compile(proxyClassFile.toString(), myProxyFile);
            Class<?> $myProxy0 = loader.findClass("$MyProxy0");
            Constructor constructor = $myProxy0.getConstructor(MyInvocationHandler.class);
            return constructor.newInstance(h);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static void compile(String content, File file) throws IOException {
        FileCopyUtils.copy(content.getBytes(), file);
        JavaCompiler javaCompiler = ToolProvider.getSystemJavaCompiler();
        StandardJavaFileManager standard = javaCompiler.getStandardFileManager(null, null, null);
        Iterable javaFileObjects = standard.getJavaFileObjects(file);
        JavaCompiler.CompilationTask task = javaCompiler.getTask(null, standard, null, null, null, javaFileObjects);
        task.call();
        standard.close();
    }

    private static String getMethodString(Method[] methods, Class<?> interfaces) {
        StringBuilder methodStringBuffer = new StringBuilder();
        for (Method method : methods) {
            methodStringBuffer.append("@Override " + RT).append("public void ").append(method.getName())
                    .append("()").append("{")
                    .append("Method method1 = ").append(interfaces.getName())
                    .append(".class.getMethod(\"").append(method.getName())
                    .append("\",new Class[]{});")
                    .append("this.h.invoke(this,method1,null);}")
                    .append(RT);
        }
        return methodStringBuffer.toString();
    }
}
