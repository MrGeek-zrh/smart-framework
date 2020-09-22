package com.smartframework.helper;

import java.lang.reflect.Field;
import java.util.Map;

import org.apache.commons.lang.ObjectUtils.Null;

import com.smartframework.annotation.Inject;
import com.smartframework.utils.ReflectionUtil;

/**
 * IOC功能助手类
* <p>Title: IOCHelper.java<／p>
* <p>Description: <／p>
* <p>Copyright: Copyright (c) 2020<／p>
* <p>Company: CUIT<／p>
* @author MrGeek
* @date 2020-09-22_20:56:28
* @version 1.0
 */
public final class IOCHelper {

	static {
		//获取所有的Class对象与Bean实例之间的关系
		Map<Class<?>, Object>beanMap = BeanHelper.getBeanMap();
		if (!beanMap.isEmpty()) {
			//便利Bean Map
			for (Map.Entry<Class<?>, Object> beanEntry: beanMap.entrySet()) {
				//获取每个bean实例和Class类
				Class<?>beanClass = beanEntry.getKey();
				Object beanInstance = beanEntry.getValue();
				//获取Class对象的所有成员变量
				Field[] fields = beanClass.getDeclaredFields();
				if (fields.length>0) {
					//便利Field数组，查找带有@Inject注解的成员变量
					for (Field field : fields) {
						//对于带有@Inject注解的成员变量，获取他的定义类型的CLass对象
						if (field.isAnnotationPresent(Inject.class)) {
							Class<?>beanFieldClass = field.getType();
							//根据Class对象从beanMap中获取到对应的bean实例
							Object beanFieldInstance = beanMap.get(beanFieldClass);
							if (beanFieldInstance!=null) {
								//将beanFieldInstance 赋值给该带有@Inject注解的成员变量,即DI完成
								ReflectionUtil.setField(beanInstance, field, beanFieldInstance);
							}
						}
					}
				}
			}
		}
	}
}
