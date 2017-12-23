package com.yiren.springmvc.annotation;

import java.lang.annotation.*;

/**
 * @author wanghao
 * create 2017-12-23 17:32
 **/
@Documented
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestMapping {
    String value();
}
