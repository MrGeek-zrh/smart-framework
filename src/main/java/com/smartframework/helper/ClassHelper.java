package com.smartframework.helper;

import java.util.HashSet;
import java.util.Set;

import com.smartframework.annotation.Controller;
import com.smartframework.annotation.Service;
import com.smartframework.utils.ClassUtil;

/**
 * 用于获取指定包下的所有类、所有Service类、所有Controller类：
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
}
