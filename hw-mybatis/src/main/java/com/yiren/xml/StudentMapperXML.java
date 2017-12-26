package com.yiren.xml;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wanghao
 * create 2017-12-25 10:47
 **/
public class StudentMapperXML {
    public static final String namespace = "com.yiren.mapper.StudentMapper";

    private static Map<String,String> methodSqlMap = new HashMap<>();

    static {
        methodSqlMap.put("findStudentById","select * from hw_student where id = %s");
    }

    public static String getMethodSQL(String method){
        return methodSqlMap.get(method);
    }


}
