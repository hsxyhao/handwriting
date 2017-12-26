package com.yiren.springmvc.servlet;

import com.yiren.springmvc.annotation.*;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wanghao
 * create 2017-12-23 17:31
 **/
@WebServlet(name = "dispatcherServlet", urlPatterns = "/", loadOnStartup = 1, initParams = {
        @WebInitParam(name = "base-package", value = "com.yiren.springmvc")
})
public class DispatcherServlet extends HttpServlet {
    /**
     * 基础包路径下所带的包权限定类名
     */
    private List<String> packageNames = new ArrayList<>();
    /**
     * 注解实例化对象
     */
    private Map<String, Object> instanceMap = new HashMap<>();
    /**
     * 带包路径权限限定名称:注解上的名称
     */
    private Map<String, String> nameMap = new HashMap<>();
    /**
     * URL地址和方法映射的关系 SpringMvc就是方法调用链
     */
    private Map<String, Method> urlMethodMap = new HashMap<>();
    /**
     * Method和权限定类名的映射关系 主要是为了通过Method找到该方法对应的对象
     */
    private Map<Method, String> methodPackageMap = new HashMap<>();

    @Override
    public void init(ServletConfig config) throws ServletException {
        String basePackage = config.getInitParameter("base-package");
        try {
            scanBasePackage(basePackage);
            instance(packageNames);
            springIOC();
            handlerUrlMethodMap();
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }

    }

    private void handlerUrlMethodMap() throws ClassNotFoundException {
        if (packageNames.size() < 1) {
            return;
        }
        for (String string : packageNames) {
            Class<?> c = Class.forName(string);
            Method[] methods = c.getMethods();
            StringBuilder baseUrl = new StringBuilder();
            if (c.isAnnotationPresent(RequestMapping.class)) {
                RequestMapping requestMapping = c.getAnnotation(RequestMapping.class);
                baseUrl.append(requestMapping.value());
            }
            for (Method method : methods) {
                if (method.isAnnotationPresent(RequestMapping.class)) {
                    RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
                    baseUrl.append(requestMapping.value());
                    urlMethodMap.put(baseUrl.toString(), method);
                    methodPackageMap.put(method, string);
                }
            }
        }
    }

    private void springIOC() throws IllegalAccessException {
        for (Map.Entry<String, Object> entry : instanceMap.entrySet()) {
            Field[] fields = entry.getValue().getClass().getDeclaredFields();
            for (Field field : fields) {
                if (field.isAnnotationPresent(Qualifier.class)) {
                    String name = field.getAnnotation(Qualifier.class).value();
                    field.setAccessible(true);
                    field.set(entry.getValue(), instanceMap.get(name));
                }
            }
        }
    }

    private void instance(List<String> packageNames) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        if (packageNames.size() < 1) {
            return;
        }
        for (String string : packageNames) {
            Class<?> c = Class.forName(string);
            String key = null;
            Object obj = null;
            String annotationName = null;
            if (c.isAnnotationPresent(Controller.class)) {
                Controller controller = c.getAnnotation(Controller.class);
                key = controller.value();
                obj = c.newInstance();
                annotationName = "Controller";
            } else if (c.isAnnotationPresent(Service.class)) {
                Service service = c.getAnnotation(Service.class);
                key = service.value();
                obj = c.newInstance();
                annotationName = "Service";
            } else if (c.isAnnotationPresent(Repository.class)) {
                Repository repository = c.getAnnotation(Repository.class);
                key = repository.value();
                obj = c.newInstance();
                annotationName = "Repository";
            }
            if (key == null || obj == null) {
                continue;
            }
            instanceMap.put(key, obj);
            nameMap.put(string, key);
            System.out.println(annotationName + " : " + string + ",value : " + key);
        }
    }

    private void scanBasePackage(String basePackage) {
        URL url = this.getClass().getClassLoader().getResource(basePackage.replaceAll("\\.", "/"));
        if (url == null) {
            return;
        }
        File basePackageFile = new File(url.getPath());
        System.out.println("scan:" + basePackageFile);
        File[] childFiles = basePackageFile.listFiles();
        if (childFiles == null || childFiles.length == 0) {
            return;
        }
        for (File file : childFiles) {
            if (file.isDirectory()) {
                scanBasePackage(basePackage + "." + file.getName());
            } else if (file.isFile()) {
                packageNames.add(basePackage + "." + file.getName().split("\\.")[0]);
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI();
        String contextPath = req.getContextPath();
        String path = uri.replaceAll(contextPath, "");

        Method method = urlMethodMap.get(path);
        if (method != null) {
            String packageName = methodPackageMap.get(method);
            String controllerName = nameMap.get(packageName);

            Object handler = instanceMap.get(controllerName);
            System.out.println(handler);
            try {
                method.setAccessible(true);
                System.out.println(method);
                method.invoke(handler);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }
}
