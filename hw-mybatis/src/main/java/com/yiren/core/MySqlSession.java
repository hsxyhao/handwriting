package com.yiren.core;

/**
 * @author wanghao
 * create 2017-12-25 10:34
 **/
public interface MySqlSession {

    /**
     * 查询一条结果集
     * @param var1 参数
     * @param <T> 返回值类型
     * @return 查询结果
     */
    <T> T selectOne(String var1);

    /**
     * 获取xml映射
     * @param var1 参数
     * @param <T> 参数类型
     * @return 映射对象
     */
    <T> T getMapper(Class<?> var1);
}
