package com.yiren.springmvc.annotation;

import java.lang.annotation.*;

/**
 * @author wanghao
 * create 2017-12-23 17:31
 **/

@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Qualifier {
    String value();
}
