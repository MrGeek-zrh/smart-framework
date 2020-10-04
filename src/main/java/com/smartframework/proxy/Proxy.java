package com.smartframework.proxy;

/**
 * 代理接口
* <p>Title: Proxy.java<／p>
* <p>Description: <／p>
* <p>Copyright: Copyright (c) 2020<／p>
* <p>Company: CUIT<／p>
* @author MrGeek
* @date 2020-10-04_21:19:42
* @version 1.0
 */
public interface Proxy {

	/**
	 * 
	* <p>Title: doProxy<／p>
	* <p>Description: <／p>
	* @param proxyChain
	* @return	代理对象的相应方法的执行结果
	* @throws Throwable
	 */
	Object doProxy(ProxyChain proxyChain) throws Throwable;

}
