package com.smartframework.helper;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.smartframework.utils.ReflectionUtil;

/**
 * Bean 助手类：
 * 		主要成员变量BEAN_MAP 记录了Class对象 与 对应的 bean实例之间的映射关系，并提供了适当的方法，用于根据Class对象获取bean实例
* <p>Title: BeanHelper.java<／p>
* <p>Description: <／p>
* <p>Copyright: Copyright (c) 2020<／p>
* <p>Company: CUIT<／p>
* @author MrGeek
* @date 2020-09-22_20:19:18
* @version 1.0
 */
public class BeanHelper {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(BeanHelper.class);
	
	//创建Map集合，用于记录CLass对象 与对应的实体类对象的映射关系
	//充当整个框架的容器
	private static final Map<Class<?>, Object>BEAN_MAP = new HashMap<Class<?>, Object>();
	
	static {
		//获取所有的Bean Class对象
		Set<Class<?>>beanClassSet = ClassHelper.getBeanClassSet();
		//遍历所有的Class对象，以便于为每个Class对象对应的bean类创建对象
		for (Class<?> cls: beanClassSet) {
			Object obj = ReflectionUtil.newInstance(cls);
			
			//为每个Class对象和对应的bean实例建立映射关系
			BEAN_MAP.put(cls, obj);
		}
	}
	
	/**
	 * 返回映射关系
	* <p>Title: getBeanMap<／p>
	* <p>Description: <／p>
	* @return
	 */
	public static Map<Class<?>, Object> getBeanMap() {
		return BEAN_MAP;
	}
	
	/**
	 * 根据CLass对象获取对应的Bean实例
	* <p>Title: getBean<／p>
	* <p>Description: <／p>
	* @param <T>
	* @param cls
	* @return
	 */
	//忽略
	@SuppressWarnings("unchecked")
	public static <T>T getBean(Class<T> cls) {
		if (!BEAN_MAP.containsKey(cls)) {
			LOGGER.error("can not get bean by class");
			throw new RuntimeException();
		}
		return (T)BEAN_MAP.get(cls);
	}
	
	/**
	 * 添加Bean实例
	* <p>Title: setBean<／p>
	* <p>Description: <／p>
	* @param cls
	* @param obj
	 */
	public static void setBean(Class<?>cls,Object obj) {
		BEAN_MAP.put(cls, obj);
	}
}
