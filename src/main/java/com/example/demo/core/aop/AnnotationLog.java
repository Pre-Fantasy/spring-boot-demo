package com.example.demo.core.aop;

import java.lang.annotation.*;

/**
 * @author  Pre_fantasy
 * @create  2018-07-04 20:07
 * @desc    remark为记录的备注
 **/
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AnnotationLog {
    String remark() default "";
}
