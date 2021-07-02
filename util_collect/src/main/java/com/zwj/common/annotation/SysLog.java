package com.zwj.common.annotation;

import java.lang.annotation.*;

/**
 * @ClassName：OperateLog
 * @Description：自定义操作日志注解，用于Cotroller
 * @Copyright：Copyright(c) 2019
 * @Company：中再云图科技有限公司
 * @Author：diaoy
 * @Date：2019/12/11
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})  //注解声明在方法之上
@Documented
public @interface SysLog {
    //描述
    String description() default "" ;
    // 日志类型
    String type() default "操作日志";
}
