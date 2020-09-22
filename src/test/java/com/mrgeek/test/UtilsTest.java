package com.mrgeek.test;

import java.util.Set;

import org.junit.Test;

import com.smartframework.utils.ClassUtil;

/**
 * Utils包测试类
 * 		主要用于测试 Utils工具类 的功能
* <p>Title: UtilsTest.java<／p>
* <p>Description: <／p>
* <p>Copyright: Copyright (c) 2020<／p>
* <p>Company: CUIT<／p>
* @author MrGeek
* @date 2020-09-22_09:43:26
* @version 1.0
 */
public class UtilsTest {
	/**
	 * ClassUtil工具类测试类
	* <p>Title: testClassUtil<／p>
	* <p>Description: <／p>
	* @throws Exception
	 */
	@Test
	public void testClassUtil() throws Exception {
		String packageName = "com.smartframework.utils";
		Set<Class<?>> classSet = ClassUtil.getClassSet(packageName);
		for (Class<?> class1 : classSet) {
			System.out.println(class1);
		}
	}
	
}
