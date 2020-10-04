package com.smartframework.proxy;

import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 切面代理类的抽象类：
 * 		其他代理类有选择的实现该抽象类的某些方法即可
* <p>Title: AspectProxy.java<／p>
* <p>Description: <／p>
* <p>Copyright: Copyright (c) 2020<／p>
* <p>Company: CUIT<／p>
* @author MrGeek
* @date 2020-10-04_21:54:41
* @version 1.0
 */
public abstract class AspectProxy implements Proxy{
	private static final Logger LOGGER = LoggerFactory.getLogger(AspectProxy.class);
	
	//实现一下Proxy的doProxy方法
	@Override
	public Object doProxy(ProxyChain proxyChain) throws Throwable {
		Object result = null;
		
		Class<?>cls = proxyChain.getTargetClass();
		Method method = proxyChain.getTargetMethod();
		Object[] params = proxyChain.getMethodParams();
		
		begin();
		try {
			//判读是否需要进行拦截
			if (intercept(cls, method, params)) {
				//需要拦截
				before(cls, method, params);
				result = proxyChain.doProxyChain();
				after(cls, method, params);
				//不需要拦截
			}else {
				result = proxyChain.doProxyChain();
			}
		} catch (Exception e) {
			LOGGER.error("proxy failure",e);
			error(cls, method, params,e);
			throw e;
		}finally {
			end();
		}
		return result;
	}

	public void begin() {
		// TODO Auto-generated method stub
	}
	
	public boolean intercept(Class<?>cls,Method method,Object[] params)throws Throwable{
		return true;
	}
	
	public void before(Class<?>cls,Method method,Object[]params)throws Throwable{
	}
	
	public void after(Class<?>cls,Method method,Object[]params)throws Throwable{
	}
	
	public void error(Class<?>cls,Method method,Object[]params,Throwable e)throws Throwable{
	}
	
	public void end() {
		// TODO Auto-generated method stub
	}
}
