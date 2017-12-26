package com.yiren.core;

import java.lang.reflect.Proxy;

/**
 * @author wanghao
 * create 2017-12-25 10:37
 **/
public class MyDefaultSqlSession implements MySqlSession {

    private MyExecutor executor = new MyBaseExecutor();

    @Override
    public <T> T selectOne(String var1) {
        return executor.query(var1);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T getMapper(Class<?> var1) {
        return (T) Proxy.newProxyInstance(this.getClass().getClassLoader(),new Class[]{
                var1
        },new MyMapperProxy(this));
    }
}
