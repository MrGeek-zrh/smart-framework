package com.smartframework.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 反射工具类：
 * 		主要用于封装java反射相关的API
* <p>Title: ReflectionUtil.java<／p>
* <p>Description: <／p>
* <p>Copyright: Copyright (c) 2020<／p>
* <p>Company: CUIT<／p>
* @author MrGeek
* @date 2020-09-22_19:58:24
* @version 1.0
 */
public final class ReflectionUtil {

	private static final Logger LOGGER = LoggerFactory.getLogger(ReflectionUtil.class);
	
	/**
	 * 通过Class对象为类创建对象：
	 * 		借助Class类的无参构造方法
	* <p>Title: newInstance<／p>
	* <p>Description: <／p>
	* @param cls
	* @return 类的对象
	 */
	public static Object newInstance(Class<?>cls) {
		Object instance;
		try {
			//调用Class对象自带的的newInstance 方法，为Class对象对应的类创建对象
			instance = cls.newInstance();
		} catch (Exception e) {
			LOGGER.error("new instance failure",e);
			throw new RuntimeException(e);
		}
		return instance;
	}
	
	/**
	 * 执行 bean实例 的方法
	* <p>Title: invokeMethod<／p>
	* <p>Description: <／p>
	* @param obj bean实例
	* @param method Class对象的Method属性
	* @param args 可变参数
	* @return Method的执行结果
	 */
	public static Object invokeMethod(Object obj,Method method,Object... args) {
		//方法的执行结果
		Object result;
		method.setAccessible(true);
		try {
			result = method.invoke(obj, args);
		} catch (Exception e) {
			LOGGER.error("invoke method failure",e);
			throw new RuntimeException(e);
		}
		return result;
	}
	
	/**
	 * 为Class对象的成员变量赋值
	* <p>Title: setField<／p>
	* <p>Description: <／p>
	* @param obj bean实例
	* @param field Class的某一个成员变量
	* @param value 想要设置的值
	 */
	public static void setField(Object obj,Field field,Object value) {
		field.setAccessible(true);
		try {
			field.set(obj, value);
		} catch (Exception e) {
			LOGGER.error("set field failure",e);
			throw new RuntimeException(e);
		}
	}
}
