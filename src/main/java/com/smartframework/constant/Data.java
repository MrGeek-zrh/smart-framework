package com.smartframework.constant;

/**
 * 数据封装类：
 * 		当返回结果为JSON数据时
* <p>Title: Data.java<／p>
* <p>Description: <／p>
* <p>Copyright: Copyright (c) 2020<／p>
* <p>Company: CUIT<／p>
* @author MrGeek
* @date 2020-09-22_22:40:34
* @version 1.0
 */
public class Data {

	private Object model;
	
	public Data(Object model) {
		this.model = model;
	}
	
	public Object getModel() {
		return model;
	}
}
