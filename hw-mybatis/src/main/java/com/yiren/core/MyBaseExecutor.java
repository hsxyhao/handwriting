package com.yiren.core;

import com.yiren.model.Student;

import java.sql.*;

/**
 * @author wanghao
 * create 2017-12-25 10:16
 **/
public class MyBaseExecutor implements MyExecutor {
    private static final String CONNECTION_URL = "jdbc:mysql://localhost:3306/jianshu?useSSL=false&useUnicode=true&characterEncoding=UTF-8&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    @SuppressWarnings("unchecked")
    @Override
    public <T> T query(String statement) {
        try (
                Connection conn = DriverManager.getConnection(CONNECTION_URL, USERNAME, PASSWORD);
                PreparedStatement preparedStatement = conn.prepareStatement(statement);
                ResultSet resultSet = preparedStatement.executeQuery()
        ) {
            Student student = null;
            if (resultSet.next()) {
                student = new Student();
                student.setId(Long.parseLong(resultSet.getString("id")));
                student.setName(resultSet.getString("name"));
                student.setAge(Integer.parseInt(resultSet.getString("age")));
                student.setAddress(resultSet.getString("address"));
                student.setSex(resultSet.getString("sex"));
            }
            return (T) student;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
