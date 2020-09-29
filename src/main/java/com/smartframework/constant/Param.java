package com.smartframework.constant;

import java.util.Map;

import com.smartframework.utils.CastUtil;

/**
 * 参数封装类:
 * 		主要用于封装Map集合，并提供适当的方法以获取执行参数的值
* <p>Title: Param.java<／p>
* <p>Description: <／p>
* <p>Copyright: Copyright (c) 2020<／p>
* <p>Company: CUIT<／p>
* @author MrGeek
* @date 2020-09-22_22:30:59
* @version 1.0
 */
public class Param {

	//用于存储所有参数的Map集合
	private Map<String, Object>paramMap;
	
	public Param(Map<String, Object>paramMap) {
		this.paramMap = paramMap;
	}
	
	/**
	 * 根据参数名获取参数值
	* <p>Title: getLong<／p>
	* <p>Description: <／p>
	* @param name
	* @return
	 */
	public long getLong(String name) {
		return CastUtil.CastToLong(paramMap.get(name));
	}
	
	/**
	 * 获取所有的参数
	* <p>Title: getMap<／p>
	* <p>Description: <／p>
	* @return
	 */
	public Map<String, Object> getMap(){
		return paramMap;
	}
}
