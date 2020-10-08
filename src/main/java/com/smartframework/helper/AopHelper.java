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
import com.smartframework.annotation.Service;
import com.smartframework.proxy.AspectProxy;
import com.smartframework.proxy.Proxy;
import com.smartframework.proxy.ProxyManager;
import com.smartframework.proxy.TransactionProxy;

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
			//获取代理类与代理实例们之间的映射关系
			Map<Class<?>, Set<Class<?>>>proxyMap = createProxyMap();
			//获取代理实例与代理类们的映射关系
			Map<Class<?>, List<Proxy>>targetMap = createTragetMap(proxyMap);
			//遍历targetMap，获取每一个代理实例和对应的代理对象，并根据代理实例和代理对象集合生成对应的代理类
			for (Map.Entry<Class<?>, List<Proxy>> targetEntry: targetMap.entrySet()) {
				//获取代理实例
				Class<?>targetClass = targetEntry.getKey();
				//获取代理对象集合
				List<Proxy>proxyList = targetEntry.getValue();
				//生成代理类
				Object proxy = ProxyManager.createProxy(targetClass, proxyList);
				//将代理实例与代理类放进框架的容器中
				BeanHelper.setBean(targetClass, proxy);
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
		//定义一个注解类，用于获取Aspect注解的value值
		Class<? extends Annotation>annotation = aspect.value();
		if (annotation!=null&&!annotation.equals(Aspect.class)) {
			//获取带有 Aspect注解的value对应的注解 的所有Class对象
			targetClassSet.addAll(ClassHelper.getClassSetByAnnotation(annotation));
		}
		return targetClassSet;
	}
	
	/**
	 * 一个代理类可以代理多个被代理类
	 * 获取代理类与对应的被代理类们之间的映射关系：
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
		//将切面代理类与所有被代理的代理实例放进Map集合中
		addAspectProxy(proxyMap);
		//将事务代理类与所有的Service类放进Map集合中
		addTransactionProxy(proxyMap);
		return proxyMap;
	}
	
	/**
	 * 一个被代理类可以被多个代理类代理
	 * 用于获取被代理类与代理类们之间的映射关系：
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
	
	/**
	 * 向Map集合中添加切面代理类：
	 * 		将切面代理类与所有的被代理的代理实例存进Map集合
	* <p>Title: addAspectProxy<／p>
	* <p>Description: <／p>
	* @param proxyMap
	* @throws Exception
	 */
	private static void addAspectProxy(Map<Class<?>, Set<Class<?>>>proxyMap) throws Exception{
		//获取所有的切面代理类（AspectProxy的子类）
		Set<Class<?>>proxyClassSet = ClassHelper.getClassSetBySuper(AspectProxy.class);
		//遍历Set集合，对于有Aspect注解的类，获取Aspect注解，并根据Aspect注解对应的所有代理实例
		for (Class<?> proxyClass : proxyClassSet) {
			//判断是否有Aspect注解
			if (proxyClass.isAnnotationPresent(Aspect.class)) {
				//获取Aspect注解
				Aspect aspect = proxyClass.getAnnotation(Aspect.class);
				//根据Aspect注解获取相应的所有的代理实例
				Set<Class<?>>targetClassSet = createTargetClassSet(aspect);
				//将代理对象与代理实例集合存放到Map集合中
				proxyMap.put(proxyClass, targetClassSet);
			}
		}
	}
	
	/**
	 * 想Map集合中添加事务代理类：
	 * 		将和事务代理类和所有的Service类存入Map集合
	* <p>Title: addTransactionProxy<／p>
	* <p>Description: <／p>
	* @param proxyMap
	 */
	private static void addTransactionProxy(Map<Class<?>, Set<Class<?>>>proxyMap) {
		//获取所有的Service类
		Set<Class<?>>serviceClassSet = ClassHelper.getClassSetByAnnotation(Service.class);
		proxyMap.put(TransactionProxy.class, serviceClassSet);
	}
}
