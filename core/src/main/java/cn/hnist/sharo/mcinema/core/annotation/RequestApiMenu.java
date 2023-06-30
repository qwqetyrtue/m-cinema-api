package cn.hnist.sharo.mcinema.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 给API方法做注解
 */

// 注解的对象
@Target({ElementType.METHOD})
// 注解的生命周期
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestApiMenu {
    // 菜单项
    String[] menu() default {};
}
