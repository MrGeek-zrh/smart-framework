package com.smartframework.helper;

import java.util.Properties;

import com.smartframework.constant.ConfigConstant;
import com.smartframework.utils.PropsUtil;

/**
 * 配置文件读取类：
 * 		主要用于获取配置文件中属性的值（借助工具类PropsUtil实现）
* <p>Title: ConfigHelper.java<／p>
* <p>Description: <／p>
* <p>Copyright: Copyright (c) 2020<／p>
* <p>Company: CUIT<／p>
* @author MrGeek
* @date 2020-09-22_00:54:52
* @version 1.0
 */
public final class ConfigHelper {

	//获取到存有配置文件中属性信息的Properties集合
	private static final Properties CONFIG_PROPS = PropsUtil.loadProps(ConfigConstant.CONFIG_FILE);
	
	/**
	 * 获取配置文件中的JDBC驱动
	* <p>Title: getJDBCDriver<／p>
	* <p>Description: <／p>
	* @return
	 */
	public static String getJDBCDriver() {
		return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.JDBC_DRIVER);
	}
	
	/**
	 * 获取配置文件中JDBC的url
	* <p>Title: getJDBCUrl<／p>
	* <p>Description: <／p>
	* @return
	 */
	public static String getJDBCUrl() {
		return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.JDBC_URL);
	}
	
	/**
	 * 获取配置文件中JDBC的username
	* <p>Title: getJDBCUsername<／p>
	* <p>Description: <／p>
	* @return
	 */
	public static String getJDBCUsername() {
		return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.JDBC_USERNAME);
	}
	
	/**
	 * 获取配置文件中JDBC的password
	* <p>Title: getJDBCPassword<／p>
	* <p>Description: <／p>
	* @return
	 */
	public static String getJDBCPassword() {
		return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.JDBC_PASSWORD);
	}
	
	/**
	 * 获取配置文件中web程序的基础包名，以便于确定以后需要扫描的包的位置
	* <p>Title: getAppBasePackage<／p>
	* <p>Description: <／p>
	* @return
	 */
	public static String getAppBasePackage() {
		return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.APP_BASE_PACKAGE);
	}
	
	/**
	 * 获取配置文件中web程序中的jsp文件的基础位置
	* <p>Title: getAPPJspBasePath<／p>
	* <p>Description: <／p>
	* @return
	 */
	public static String getAPPJspBasePath() {
		return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.APP_JSP_BASE_PATH);
	}
	
	/**
	 * 获取配置文件中web程序中的静态资源文件的基础位置
	* <p>Title: getAPPStaticResourceBasePath<／p>
	* <p>Description: <／p>
	* @return
	 */
	public static String getAPPStaticResourceBasePath() {
		return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.APP_STATICRESOURCE_BASE_PATH);
	}
	
}
