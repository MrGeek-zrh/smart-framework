package com.smartframework.proxy;

import java.lang.reflect.Method;
import java.util.List;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

/**
 * 代理管理器
* <p>Title: ProxyManager.java<／p>
* <p>Description: <／p>
* <p>Copyright: Copyright (c) 2020<／p>
* <p>Company: CUIT<／p>
* @author MrGeek
* @date 2020-10-04_21:43:26
* @version 1.0
 */
public class ProxyManager {

	/**
	 * 生成代理对象
	* <p>Title: createProxy<／p>
	* <p>Description: <／p>
	* @param <T>
	* @param targetClass
	* @param proxyList
	* @return
	 */
	@SuppressWarnings("unchecked")
	public static <T>T createProxy(final Class<?>targetClass,final List<Proxy>proxyList){
		return (T)Enhancer.create(targetClass, new MethodInterceptor() {
			
			@Override
			public Object intercept(Object targetObject, Method targetMethod, Object[] methodParams, MethodProxy methodProxy) throws Throwable {
				return new ProxyChain(targetClass, targetObject, targetMethod, methodProxy, methodParams, proxyList).doProxyChain();
			}
		});
	}
}
