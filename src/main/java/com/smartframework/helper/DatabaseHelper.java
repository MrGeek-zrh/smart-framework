package com.smartframework.helper;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mysql.jdbc.Connection;
import com.smartframework.utils.PropsUtil;

/**
 * 数据库操作类
* <p>Title: DatabaseHelper.java<／p>
* <p>Description: <／p>
* <p>Copyright: Copyright (c) 2020<／p>
* <p>Company: CUIT<／p>
* @author MrGeek
* @date 2020-10-07_20:14:53
* @version 1.0
 */
public final class DatabaseHelper {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseHelper.class);
	
	//四大属性
	private static final String DRIVER;
	private static final String URL;
	private static final String USERNAME;
	private static final String PASSWORD;
	
	//将Connection放进本地线程变量
	private static final ThreadLocal<Connection>CONNECTION_HOLDER ;
	//创建数据库连接池
	private static final BasicDataSource DATA_SOURCE;
	
	static {
		CONNECTION_HOLDER = new ThreadLocal<Connection>();
		
		Properties conf = PropsUtil.loadProps("jdbc.properties");
		DRIVER = conf.getProperty("jdbc.driver");
		URL = conf.getProperty("jdbc.url");
		USERNAME = conf.getProperty("jdbc.username");
		PASSWORD = conf.getProperty("jdbc.password");
		
		DATA_SOURCE = new BasicDataSource();
		DATA_SOURCE.setDriverClassName(DRIVER);
		DATA_SOURCE.setUrl(URL);
		DATA_SOURCE.setUsername(USERNAME);
		DATA_SOURCE.setPassword(PASSWORD);
		
		try {
			Class.forName(DRIVER);
		} catch (ClassNotFoundException e) {
			LOGGER.error("can not find jdbc driver",e);
		}
	}
	
	/**
	 * 和数据库建立链接
	* <p>Title: getConnection<／p>
	* <p>Description: <／p>
	* @return 和数据库建立的连接
	 */
	public static Connection getConnection() {
		Connection conn = CONNECTION_HOLDER.get();
		if (conn == null) {
			try {
				//获取数据库链接
				conn = (Connection) DATA_SOURCE.getConnection();
			} catch (SQLException e) {
				LOGGER.error("get connection error",e);
				throw new RuntimeException(e);
			}finally {
				CONNECTION_HOLDER.set(conn);
			}
		}
		
		return conn;
	}
	
	/**
	 * 关闭数据库链接
	* <p>Title: closeConnection<／p>
	* <p>Description: <／p>
	 */
	public static void closeConnection(Connection conn) {
		if (conn!=null) {
			try {
				conn.close();
			} catch (SQLException e) {
				LOGGER.error("close database connection failure",e);
				throw new RuntimeException(e);
			}finally {
				CONNECTION_HOLDER.remove();
			}
		}
	}
	
	/**
	 * 开启事务
	* <p>Title: beginTransaction<／p>
	* <p>Description: <／p>
	 */
	public static void beginTransaction() {
		Connection conn = getConnection();
		if (conn!=null) {
			try {
				//关闭自动提交事务机制
				conn.setAutoCommit(false);
			} catch (SQLException e) {
				LOGGER.error("begin transaction failure",e);
				throw new RuntimeException(e);
			}finally {
				CONNECTION_HOLDER.set(conn);
			}
		}
	}
	
	/**
	 * 提交事务
	* <p>Title: commitTransaction<／p>
	* <p>Description: <／p>
	 */
	public static void commitTransaction() {
		Connection conn = getConnection();
		if (conn!=null) {
			try {
				conn.commit();
				conn.close();
			} catch (SQLException e) {
				LOGGER.error("commit transaction failure",e);
				throw new RuntimeException(e);
			}finally {
				CONNECTION_HOLDER.remove();
			}
		}
	}
	
	/**
	 * 回滚事务
	* <p>Title: rollbackTransaction<／p>
	* <p>Description: <／p>
	 */
	public static void rollbackTransaction() {
		Connection conn = getConnection();
		if (conn!=null) {
			try {
				conn.rollback();
				conn.close();
			} catch (SQLException e) {
				LOGGER.error("rollback transaction failure",e);
				throw new RuntimeException(e);
			}finally {
				CONNECTION_HOLDER.remove();
			}
		}
	}
	
}
