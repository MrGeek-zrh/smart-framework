package com.smartframework.annotation;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 切面注解
* <p>Title: Aspect.java<／p>
* <p>Description: <／p>
* <p>Copyright: Copyright (c) 2020<／p>
* <p>Company: CUIT<／p>
* @author MrGeek
* @date 2020-10-04_21:16:00
* @version 1.0
 */
//限定只能用在类上
@Target(ElementType.TYPE)
//生命周期为运行时
@Retention(RetentionPolicy.RUNTIME)
public @interface Aspect {
	/*
	 * 属性为注解类型
	 */
	Class<? extends Annotation>value();
}
