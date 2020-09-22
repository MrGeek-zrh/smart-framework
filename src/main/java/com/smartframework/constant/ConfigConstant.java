package com.smartframework.constant;

/**
 * 配置文件助手类(实体类)：
 * 	和配置文件中的各个属性相对应，主要是为了方便管理配置文件的内容
* <p>Title: ConfigConstant.java<／p>
* <p>Description: <／p>
* <p>Copyright: Copyright (c) 2020<／p>
* <p>Company: CUIT<／p>
* @author MrGeek
* @date 2020-09-21_23:35:34
* @version 1.0
 */
public interface ConfigConstant {
	
	String CONFIG_FILE = "smart.properties";
	
	String JDBC_DRIVER = "smart.framework.jdbc.driver";
	String JDBC_URL = "smart.framework.jdbc.url";
	String JDBC_USERNAME = "smart.framework.jdbc.username";
	String JDBC_PASSWORD = "smart.framework.jdbc.password";
	String APP_BASE_PACKAGE = "smart.framework.app.base_package";
	String APP_JSP_BASE_PATH = "smart.framework.app.jsp_base_path";
	String APP_STATICRESOURCE_BASE_PATH = "smart.framework.app.staticresource_base_path";
}
