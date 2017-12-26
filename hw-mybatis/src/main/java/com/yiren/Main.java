package com.yiren;

import com.yiren.core.MyDefaultSqlSession;
import com.yiren.core.MySqlSession;
import com.yiren.mapper.StudentMapper;
import com.yiren.model.Student;

/**
 * @author wanghao
 * create 2017-12-25 11:01
 **/
public class Main {
    public static void main(String[] args){
        MySqlSession mySqlSession = new MyDefaultSqlSession();
        StudentMapper studentMapper = mySqlSession.getMapper(StudentMapper.class);
        Student student = studentMapper.findStudentById(1);
        System.out.println(student);
    }
}
