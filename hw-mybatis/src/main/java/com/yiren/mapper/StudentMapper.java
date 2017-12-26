package com.yiren.mapper;

import com.yiren.model.Student;

/**
 * @author wanghao
 * create 2017-12-25 10:47
 **/
public interface StudentMapper {

    Student findStudentById(int id);
    void insertStudent(Student student);

}
