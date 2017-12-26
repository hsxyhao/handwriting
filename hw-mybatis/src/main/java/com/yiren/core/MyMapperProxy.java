package com.yiren.core;

import com.yiren.mapper.StudentMapper;
import com.yiren.xml.StudentMapperXML;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author wanghao
 * create 2017-12-25 10:39
 **/
public class MyMapperProxy implements InvocationHandler {

    private MySqlSession mySqlSession;

    public MyMapperProxy(MySqlSession mySqlSession) {
        this.mySqlSession = mySqlSession;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String mapperClass = method.getDeclaringClass().getName();
        if (StudentMapperXML.namespace.equals(mapperClass)){
            String methodName = method.getName();
            String originSql = StudentMapperXML.getMethodSQL(methodName);

            String formatterSql = String.format(originSql,args[0].toString());
            return mySqlSession.selectOne(formatterSql);
        }

        return null;
    }
}
