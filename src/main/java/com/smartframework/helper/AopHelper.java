package com.smartframework.helper;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.smartframework.annotation.Aspect;
import com.smartframework.proxy.AspectProxy;
import com.smartframework.proxy.Proxy;
import com.smartframework.proxy.ProxyManager;

/**
 * AOP功能实现类
* <p>Title: AopHelper.java<／p>
* <p>Description: <／p>
* <p>Copyright: Copyright (c) 2020<／p>
* <p>Company: CUIT<／p>
* @author MrGeek
* @date 2020-10-04_22:35:01
* @version 1.0
 */
public final class AopHelper {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AopHelper.class);

	static {
		try {
			Map<Class<?>, Set<Class<?>>>proxyMap = createProxyMap();
			Map<Class<?>, List<Proxy>>targetMap = createTragetMap(proxyMap);
			for (Map.Entry<Class<?>, List<Proxy>> targetEntry: targetMap.entrySet()) {
				Class<?>targetClass = targetEntry.getKey();
				List<Proxy>proxyList = targetEntry.getValue();
				//生成代理类
				Object proxy = ProxyManager.createProxy(targetClass, proxyList);
			}
		} catch (Exception e) {
			LOGGER.error("aop failure",e);
		}
	}
	
	/**
	 * 获取带有 Aspect注解中value 注解的类的Class对象
	* <p>Title: createTargetClassSet<／p>
	* <p>Description: <／p>
	* @param aspect
	* @return Set集合，存有所有带有 Aspect注解中value 注解的类的Class对象
	 */
	private static Set<Class<?>> createTargetClassSet(Aspect aspect) {
		//用于存放所有带有 Aspect注解中value 注解的类的Class对象
		Set<Class<?>>targetClassSet = new HashSet<>();
		
		Class<? extends Annotation>annotation = aspect.value();
		if (annotation!=null&&!annotation.equals(Aspect.class)) {
			targetClassSet.addAll(ClassHelper.getClassSetByAnnotation(annotation));
		}
		return targetClassSet;
	}
	
	/**
	 * 一个代理类可以代理多个被代理类
	 * 获取代理类与对应的被代理类之间的映射关系：
	 * 		比如，有一个代理类实现了AspectProxy 抽象类，且代理类上的Aspect注解的值为Controller，那么
	 * 		被代理类就是所有的Controller类
	* <p>Title: createProxyMap<／p>
	* <p>Description: <／p>
	* @return
	* @throws Exception
	 */
	private static Map<Class<?>, Set<Class<?>>> createProxyMap() throws Exception{
		//用于存储代理类与被代理类之间的映射关系
		Map<Class<?>, Set<Class<?>>>proxyMap = new HashMap<>();
		//获取所有实现了AspectProxy抽象类的类
		Set<Class<?>>proxyClassSet = ClassHelper.getClassSetBySuper(AspectProxy.class);
		for (Class<?> proxyClass : proxyClassSet) {
			if (proxyClass.isAnnotationPresent(Aspect.class)) {
				//获取代理类上的注解的实例
				Aspect aspect = proxyClass.getAnnotation(Aspect.class);
				//根据代理类上的注解的实例获取被代理类集合
				Set<Class<?>>targetClassSet = createTargetClassSet(aspect);
				//将代理类与被代理类集合存放到Map集合中
				proxyMap.put(proxyClass, targetClassSet);
			}
		}
		return proxyMap;
	}
	
	/**
	 * 一个被代理类可以被多个代理类代理
	 * 用于获取被代理类与代理类之间的映射关系：
	 * 		比如一个Controller类，被多个代理类代理
	* <p>Title: createTragetMap<／p>
	* <p>Description: <／p>
	* @param proxyMap key:代理类的Class对象。value:对应的被代理类的Set集合
	* @return Map key:被代理类。value:对应的代理类的list集合
	* @throws Exception
	 */
	private static Map<Class<?>, List<Proxy>> createTragetMap(Map<Class<?>, Set<Class<?>>>proxyMap) throws Exception{
		//用于存放被代理类与代理对象之间的映射关系
		Map<Class<?>, List<Proxy>>targetMap = new HashMap<>();
		//遍历代理类与被代理类的Map集合，将一个被代理类以及他所对应的所有的代理类以key:value 
		//	的形式存到Map集合中
		for (Map.Entry<Class<?>, Set<Class<?>>> proxyEntry: proxyMap.entrySet()) {
			//获取代理对象
			Class<?>proxyClass = proxyEntry.getKey();
			//获取被代理类的Class对象
			Set<Class<?>>targetClassSet = proxyEntry.getValue();
			//遍历所有的被代理类，将被代理类与对应的代理类放进Map集合中
			for (Class<?> targetClass: targetClassSet) {
				//根据代理类的Class对象，创建代理对象
				Proxy proxy = (Proxy)proxyClass.newInstance();
				//判断Map集合中时候已经存在当前被代理类
				if (targetMap.containsKey(targetClass)) {
					//存在，则通过get方法，获取到对应的代理类集合，并将当前代理类放进list集合中
					targetMap.get(targetClass).add(proxy);
				}else {
					//不存在，则创建代理类集合，并将当前代理类放进list集合，然后将list集合与当前被代理类
					//放进Map集合中
					List<Proxy>proxyList = new ArrayList<>();
					proxyList.add(proxy);
					targetMap.put(targetClass, proxyList);
				}
			}
		}
		return targetMap;
	}
}
