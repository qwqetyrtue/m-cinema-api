package cn.hnist.sharo.mcinema.adminapi.annotation;

import cn.hnist.sharo.mcinema.core.model.LogEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestLog {
    LogEnum.Type type() default LogEnum.Type.GENERAL;
    LogEnum.Action action();
}
