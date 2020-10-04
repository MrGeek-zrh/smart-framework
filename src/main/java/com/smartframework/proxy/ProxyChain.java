package com.smartframework.proxy;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import net.sf.cglib.proxy.MethodProxy;

/**
 * 自定义代理链
* <p>Title: ProxyChain.java<／p>
* <p>Description: <／p>
* <p>Copyright: Copyright (c) 2020<／p>
* <p>Company: CUIT<／p>
* @author MrGeek
* @date 2020-10-04_21:21:31
* @version 1.0
 */
public class ProxyChain {

	//被代理类的Class对象
	private final Class<?> targetClass;
	//被代理类
	private final Object targetObject;
	//被代理类的方法
	private final Method targetMethod;
	//用于代理方法的代理类
	private final MethodProxy methodProxy;
	//执行方法所需要的参数
	private final Object[] methodParams;
	//代理链
	private List<Proxy> proxyList = new ArrayList<>();
	//代理计数器，用来充当代理索引
	private int proxyIndex = 0;
	
	//通过构造方法初始化成员变量
	public ProxyChain(Class<?> targetClass, Object targetObject, Method targetMethod, MethodProxy methodProxy,
			Object[] methodParams, List<Proxy> proxyList) {
		super();
		this.targetClass = targetClass;
		this.targetObject = targetObject;
		this.targetMethod = targetMethod;
		this.methodProxy = methodProxy;
		this.methodParams = methodParams;
		this.proxyList = proxyList;
	}
	
	public Object[] getMethodParams() {
		return methodParams;
	}
	
	public Class<?> getTargetClass() {
		return targetClass;
	}
	
	public Method getTargetMethod() {
		return targetMethod;
	}
	
	/**
	 * 执行代理方法，获取返回值
	* <p>Title: doProxyChain<／p>
	* <p>Description: <／p>
	* @return
	* @throws Throwable
	 */
	public Object doProxyChain() throws Throwable{
		Object methodResult;
		if (proxyIndex<proxyList.size()) {
			methodResult = proxyList.get(proxyIndex++).doProxy(this);
		}else {
			methodResult = methodProxy.invokeSuper(targetObject, methodParams);
		}
		return methodResult;
	}
}
