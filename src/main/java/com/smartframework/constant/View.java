package com.smartframework.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * 视图封装类:
 * 		主要用于封装视图的路径，以及相应的参数模型
* <p>Title: View.java<／p>
* <p>Description: <／p>
* <p>Copyright: Copyright (c) 2020<／p>
* <p>Company: CUIT<／p>
* @author MrGeek
* @date 2020-09-22_22:36:16
* @version 1.0
 */
public class View {

	//返回的视图的路径
	private String path;
	
	//模型数据
	private Map<String, Object>model = new HashMap<>();
	
	public View(String path) {
		this.path = path;
	}
	
	public View addModel(String key,Object value) {
		model.put(key, value);
		return this;
	}
	
	public String getPath() {
		return path;
	}
	
	public Map<String, Object> getModel() {
		return model;
	}
}
