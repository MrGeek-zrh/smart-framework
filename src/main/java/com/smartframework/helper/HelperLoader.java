package com.smartframework.helper;

import com.smartframework.utils.ClassUtil;

/**
 * static静态代码加载类
* <p>Title: HelperLoader.java<／p>
* <p>Description: <／p>
* <p>Copyright: Copyright (c) 2020<／p>
* <p>Company: CUIT<／p>
* @author MrGeek
* @date 2020-09-22_22:05:15
* @version 1.0
 */
public class HelperLoader {

	/**
	 * 用于加载其他Helper助手类中的static 代码
	* <p>Title: HelperLoader.java<／p>
	* <p>Description: <／p>
	 */
	public static void init() {
		Class<?>[] classList = {
				ClassHelper.class,
				BeanHelper.class,
				//AopHelper 需要在IOCHelper之前加载
				//需要先通过AopHelper获取代理对象，然后通过IocHelper进行依赖注入
				AopHelper.class,
				IOCHelper.class,
				ControllerHelper.class
		};
		
		for (Class<?> cls : classList) {
			ClassUtil.loadClass(cls.getName(), true);
		}
	}
	
}
