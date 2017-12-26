package com.yiren.core;

/**
 * @author wanghao
 * create 2017-12-25 10:13
 **/
public interface MyExecutor {
    /**
     * 执行器方法
     * @param statement 声明
     * @param <T> 返回类型
     * @return 查询返回列表
     */
    <T> T query(String statement);
}
