package com.smartframework.constant;

import java.lang.reflect.Method;

/**
 * 用于封装 当前控制器的Class对象以及用于处理请求的方法 的信息
* <p>Title: Param.java<／p>
* <p>Description: <／p>
* <p>Copyright: Copyright (c) 2020<／p>
* <p>Company: CUIT<／p>
* @author MrGeek
* @date 2020-09-22_21:39:16
* @version 1.0
 */
public class Handler {
	
	//用于处理请求的控制器的Class对象
	private Class<?>controllerClass;
	
	//用于处理请求的方法
	private Method actionMethod;
	
	public Handler(Class<?>controllerClass,Method actionMethod) {
		this.controllerClass = controllerClass;
		this.actionMethod = actionMethod;
	}
	
	public Method getActionMethod() {
		return actionMethod;
	}
	
	public Class<?> getControllerClass() {
		return controllerClass;
	}
}
