package com.smartframework.helper;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.smartframework.annotation.Action;
import com.smartframework.constant.Handler;
import com.smartframework.constant.Request;

/**
 * 控制器助手类
* <p>Title: ControllerHelper.java<／p>
* <p>Description: <／p>
* <p>Copyright: Copyright (c) 2020<／p>
* <p>Company: CUIT<／p>
* @author MrGeek
* @date 2020-09-22_21:45:21
* @version 1.0
 */
public final class ControllerHelper {
	
	//定义Map集合，用于存放请求与处理器之间的映射关系
	private static final Map<Request, Handler>ACTION_MAP = new HashMap<>();
	
	static {
		//获取所有的Controller类
		Set<Class<?>>controllerClassSet = ClassHelper.getControClassSet();
		//遍历controller类
		for (Class<?> cls : controllerClassSet) {
			//获取Controller类中定义的方法
			Method[] methods = cls.getDeclaredMethods();
			if (methods.length>0) {
				//遍历所有的方法
				for (Method method : methods) {
					//找出带有Action注解的方法
					if (method.isAnnotationPresent(Action.class)) {
						//对于带有Action注解的方法，获取Action中参数的值
						Action action = method.getAnnotation(Action.class);
						String mapping = action.value();
						//验证 URL 映射规则是否正确
						if (mapping.matches("\\w+:/\\w+")) {
							String[] array = mapping.split(":");
							if (array.length==2) {
								//获取请求路径和请求方法
								String requestMethod = array[0];
								String requestPath = array[1];
								//将请求方法与请求路径封装到Request实体类中
								Request request = new Request(requestMethod, requestPath);
								//将当前的控制器的CLass对象和当前的处理请求的方法封装到Handler中
								Handler handler = new Handler(cls, method);
								//将Request 和 Handler 封装到Map集合中
								ACTION_MAP.put(request, handler);
							}
						}
					}
				}
			}
		}
	}
	
	/**
	 * 根据Request获取对应的Handler对象
	* <p>Title: getHandler<／p>
	* <p>Description: <／p>
	* @param requestMethod
	* @param requestPath
	* @return Handler对象
	 */
	public static Handler getHandler(Request request) {
		return ACTION_MAP.get(request);
	}
}

