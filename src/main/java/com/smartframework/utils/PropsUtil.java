package com.smartframework.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 配置文件加载工具类：
 * 		主要用于加载配置文件
* <p>Title: PropsUtil.java<／p>
* <p>Description: <／p>
* <p>Copyright: Copyright (c) 2020<／p>
* <p>Company: CUIT<／p>
* @author MrGeek
* @date 2020-09-21_23:54:47
* @version 1.0
 */
public final class PropsUtil {

	//引入slf4j进行日志管理
	private static final Logger LOGGER = LoggerFactory.getLogger(PropsUtil.class);
	
	/**
	 * 加载properties文件
	* <p>Title: loadProps<／p>
	* <p>Description: <／p>
	* @param fileName：属性文件名称
	* @return 包含有属性文件中各个属性值的Properties集合
	 */
	public static Properties loadProps(String fileName) {
		
		Properties properties = null;
		InputStream is = null;
		
		try {
			//获取属性文件的流对象
			//在根据属性文件的名称查找属性文件时，默认在resource下查找
			is = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
			if (is == null) {
				throw new FileNotFoundException(fileName+"file is not found");
			}
			//获取流对象之后，将流对象中的属性以key-value形式加载进Properties集合中
			properties = new Properties();
			properties.load(is);
			
		} catch (IOException e) {
			LOGGER.error("load properties file failure",e);
		}finally {
			if (is!=null) {
				try {
					//关流
					is.close();
				} catch (IOException e) {
					LOGGER.error("close stream failure",e);
				}
			}
		}
		
		return properties;
	}
	
	/**
	 * 根据属性名获取属性值（适用于属性值是String类型的）
	 * 		如果配置文件中没有该属性，则使用默认值，默认的属性值为 “”
	* <p>Title: getString<／p>
	* <p>Description: <／p>
	* @param props
	* @param key
	* @return
	 */
	public static String getString(Properties props,String key) {
		
		String value ;
		
		if (props.containsKey(key)) {
			value = props.getProperty(key);
		}else {
			value = "";
		}
		return value;
	}
	
	/**
	 * 根据属性名获取属性值（适用于属性值是int类型的）
	 * 		如果配置文件中没有该属性，则使用默认值，默认的属性值为 0
	* <p>Title: getInt<／p>
	* <p>Description: <／p>
	* @param props
	* @param key
	* @return
	 */
	public static int getInt(Properties props,String key) {
		
		int value ;
		
		if (props.containsKey(key)) {
			value = CastUtil.CastToInt(props.getProperty(key));
		}else {
			value = 0;
		}
		return value;
	}
	
}
