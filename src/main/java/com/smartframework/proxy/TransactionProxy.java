package com.smartframework.proxy;

import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.smartframework.annotation.Transaction;
import com.smartframework.helper.DatabaseHelper;

/**
 * 代理类，用于实现事务代理
* <p>Title: TransactionProxy.java<／p>
* <p>Description: <／p>
* <p>Copyright: Copyright (c) 2020<／p>
* <p>Company: CUIT<／p>
* @author MrGeek
* @date 2020-10-07_21:15:00
* @version 1.0
 */
public class TransactionProxy implements Proxy {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(TransactionProxy.class);
	
	/**
	 * 设置本地线程变量的初始值
	 */
	private static final ThreadLocal<Boolean> FLAG_HOLDER = new ThreadLocal<Boolean>() {
		protected Boolean initialValue() {
			return false;
		}
	};

	/**
	 * 执行代理
	 */
	@Override
	public Object doProxy(ProxyChain proxyChain) throws Throwable {
		Object result;
		boolean flag = FLAG_HOLDER.get();
		Method method = proxyChain.getTargetMethod();
		if (!flag && method.isAnnotationPresent(Transaction.class)) {
			FLAG_HOLDER.set(true);
			try {
				DatabaseHelper.beginTransaction();
				LOGGER.debug("begin transaction");
				result = proxyChain.doProxyChain();
				DatabaseHelper.commitTransaction();
				LOGGER.debug("commit transaction");
			} catch (Exception e) {
				DatabaseHelper.rollbackTransaction();
				LOGGER.debug("rollback transaction");
				throw e;
			}finally {
				FLAG_HOLDER.remove();
			}
		}else {
			result = proxyChain.doProxyChain();
		}
		return result;
	}

}
