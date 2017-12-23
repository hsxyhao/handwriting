package com.yiren.springmvc.annotation;

import java.lang.annotation.*;

/**
 * @author wanghao
 * create 2017-12-23 17:31
 **/
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Controller {
    /**
     * 表示Controller的类名
     *
     * @return name
     */
    String value();
}
