package com.smartframework.helper;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;

import com.smartframework.annotation.Controller;
import com.smartframework.annotation.Service;
import com.smartframework.utils.ClassUtil;

/**
 * 用于获取指定包下的 所有类、所有Service类、所有Controller类、所有Aspect类：
 * 		借助ClassUtil工具类实现
* <p>Title: ClassHelper.java<／p>
* <p>Description: <／p>
* <p>Copyright: Copyright (c) 2020<／p>
* <p>Company: CUIT<／p>
* @author MrGeek
* @date 2020-09-22_18:20:58
* @version 1.0
 */
public final class ClassHelper {

	private static final Set<Class<?>>CLASSE_SET;
	static {
		String basePackage = ConfigHelper.getAppBasePackage();
		CLASSE_SET = ClassUtil.getClassSet(basePackage);
	}
	
	/**
	 * 获取应用包下的所有类
	* <p>Title: getClassSet<／p>
	* <p>Description: <／p>
	* @return
	 */
	public static Set<Class<?>> getClassSet() {
		return CLASSE_SET;
	}
	
	/**
	 * 获取所有Service类
	* <p>Title: getServiceClassSet<／p>
	* <p>Description: <／p>
	* @return
	 */
	public static Set<Class<?>> getServiceClassSet() {
		Set<Class<?>>classSet = new HashSet<>();
		for (Class<?> cls : CLASSE_SET) {
			if (cls.isAnnotationPresent(Service.class)) {
				classSet.add(cls);
			}
		}
		return classSet;
	}
	
	/**
	 * 获取所有Controller类
	* <p>Title: getControClassSet<／p>
	* <p>Description: <／p>
	* @return
	 */
	public static Set<Class<?>> getControClassSet() {
		Set<Class<?>>classSet = new HashSet<>();
		for (Class<?> cls : CLASSE_SET) {
			if (cls.isAnnotationPresent(Controller.class)) {
				classSet.add(cls);
			}
		}
		return classSet;
	}
	
	/**
	 * 获取所有的Bean类（Service+Controller）
	* <p>Title: getBeanClassSet<／p>
	* <p>Description: <／p>
	* @return
	 */
	public static Set<Class<?>> getBeanClassSet() {
		Set<Class<?>>beanClassSet = new HashSet<>();
		beanClassSet.addAll(getServiceClassSet());
		beanClassSet.addAll(getControClassSet());
		return beanClassSet;
	}
	
	/**
	 * 获取指定类的所有子类
	* <p>Title: getClassSetBySuper<／p>
	* <p>Description: <／p>
	* @param superClass 执行的类的Class对象
	* @return Set集合，存有指定父类的所有子类
	 */
	public static Set<Class<?>> getClassSetBySuper(Class<?>superClass) {
		Set<Class<?>>classSet = new HashSet<>();
		for (Class<?> cls : CLASSE_SET) {
			if (superClass.isAssignableFrom(cls)&&!superClass.equals(cls)) {
				classSet.add(cls);
			}
		}
		return classSet;
	}
	
	/**
	 * 获取带有指定注解的类
	* <p>Title: getClassSetByAnnotation<／p>
	* <p>Description: <／p>
	* @param annotationClass 注解类的Class对象
	* @return Set集合，存有所有带有指定注解的类的Class对象
	 */
	public static Set<Class<?>> getClassSetByAnnotation(Class<? extends Annotation>annotationClass) {
		Set<Class<?>> classSet = new HashSet<>();
		for (Class<?> cls : CLASSE_SET) {
			if (cls.isAnnotationPresent(annotationClass)) {
				classSet.add(cls);
			}
		}
		return classSet;
	}
}
