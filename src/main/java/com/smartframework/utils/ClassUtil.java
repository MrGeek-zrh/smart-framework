package com.smartframework.utils;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

import javax.management.RuntimeErrorException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 类加载器工具类：
 * 		主要用于获取指定包下的所有Class对象
* <p>Title: ClassUtil.java<／p>
* <p>Description: <／p>
* <p>Copyright: Copyright (c) 2020<／p>
* <p>Company: CUIT<／p>
* @author MrGeek
* @date 2020-09-22_01:19:31
* @version 1.0
 */
public class ClassUtil {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ClassUtil.class);

	/**
	 * 获取类加载器
	* <p>Title: getClassLoader<／p>
	* <p>Description: <／p>
	* @return ClassLoader
	 */
	public static ClassLoader getClassLoader() {
		return Thread.currentThread().getContextClassLoader();
	}
	
	/**
	 * 根据类名获取类的Class对象
	* <p>Title: loadClass<／p>
	* <p>Description: <／p>
	* @param className: String-类名（带完整包名,不带文件后缀）
	* @param isInitialized: boolean-是否初始化
	* @return CLass对象
	 */
	@SuppressWarnings("rawtypes")
	public static Class loadClass(String className,boolean isInitialized) {
		Class cls;
		try {
			cls = Class.forName(className, isInitialized, getClassLoader());
		} catch (ClassNotFoundException e) {
			LOGGER.error("load class failure",e);
			throw new RuntimeException(e);
		}
		return cls;
	}
	
	/**
	 * 获取指定包下的所有类的Class对象
	* <p>Title: getClassSet<／p>
	* <p>Description: <／p>
	* @param packageName
	* @return
	 */
	public static Set<Class<?>> getClassSet(String packageName) {
		
		//定义一个Set集合，用于存储获取到的CLass对象
		Set<Class<?>>classSet = new HashSet<Class<?>>();
		
		try {
//					暂时只针对本地文件进行处理，暂不支持获取远程文件的相关信息
			//获取指定包下的所有文件或文件夹的URL对象
			Enumeration<URL>urls = getClassLoader().getResources(packageName.replace(".", "/"));
			
			//遍历所有的URL
			while (urls.hasMoreElements()) {
				URL url = (URL) urls.nextElement();
				if (url!=null) {
					//获取url的protocol类别
					String protocol = url.getProtocol();
					//url的协议为file，说明是本地文件/文件夹
					if (protocol.equals("file")) {
						//获取文件夹/文件的路径
						String packagePath = url.getPath();
						//将CLass对象添加至classSet集合中
						addClass(classSet,packagePath,packageName);
					}
				}
			}
		} catch (Exception e) {
			LOGGER.error("load class failure!(we can only load localfile ,are you sure you are just loaded localfile ?)",e);
			throw new RuntimeException(e);
		}
		return classSet;
	}
	
	/**
	 * 用于想Set集合中添加Class对象
	 * 		同时由于对于文件夹，会涉及到递归的操作，所以这里封装为一个方法，方便实现递归
	* <p>Title: addClass<／p>
	* <p>Description: <／p>
	* @param classSet
	* @param packagePath 包的路径
	* @param packageName 包的包名
	 */
	private static void addClass(Set<Class<?>>classSet ,String packagePath ,String packageName) {
		//通过packagePath获取当前路径下的所有文件或文件夹的File对象
		File[] files = new File(packagePath).listFiles(new FileFilter() {
			//保留.class文件或者文件夹
			public boolean accept(File file) {
				return (file.isFile()&&file.getName().endsWith(".class"))||file.isDirectory();
			}
		});

		//.class文件 和 文件夹需要分开进行处理
		for (File file : files) {
			
			
			
			//当是.class文件时，直接获取到类的完整名称（带包名）,然后调用loadClass()加载即可
			if (file.isFile()) {
				String fileName = file.getName();
				//className = packageName + fileName.substring(0, fileName.indexOf("."))
				String className = packageName +"."+ fileName.substring(0, fileName.indexOf("."));  
				//获取完整文件名之后，对文件进行加载，并将获取到的Class对象放进Set<CLass>集合中
				Class<?> cls = loadClass(className, false);
				//将Class对象添加到classSet中
				classSet.add(cls);
			}
			//当是文件夹时,需要进行递归
			if (file.isDirectory()) {
				//重新设置packagePath
				String subpackagePath = packagePath+"/"+file.getName();
				//重新设置packageName
				String subpackageName = packageName+"."+file.getName();
				//递归调用当前方法
				addClass(classSet, subpackagePath, subpackageName);
			}
		}
	}
	
}
