package com.smartframework.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * JSON工具类:
 * 		主要用于 POJO 和 JSON 之间的互相转换
* <p>Title: JsonUtil.java<／p>
* <p>Description: <／p>
* <p>Copyright: Copyright (c) 2020<／p>
* <p>Company: CUIT<／p>
* @author MrGeek
* @date 2020-09-22_23:47:13
* @version 1.0
 */
public class JsonUtil {

	private static final Logger LOGGER = LoggerFactory.getLogger(JsonUtil.class);
	
	private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
	
	/**
	 * POJO 转 JSON
	* <p>Title: toJson<／p>
	* <p>Description: <／p>
	* @param <T>
	* @param obj
	* @return
	 */
	public static <T>String toJson(T obj) {
		String josn;
		try {
			josn = OBJECT_MAPPER.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			LOGGER.error("convert POJO to JSON failure",e);
			throw new RuntimeException(e);
		}
		return josn;
	}
	
	/**
	 * JSON 转 POJO
	* <p>Title: fromJson<／p>
	* <p>Description: <／p>
	* @param <T>
	* @param json
	* @param type
	* @return
	 */
	public static <T>T fromJson(String json,Class<T>type) {
		T pojo;
		try {
			pojo = OBJECT_MAPPER.readValue(json, type);
		} catch (Exception e) {
			LOGGER.error("convert JSON to POJO failure",e);
			throw new RuntimeException(e);
		} 
		return pojo;
	}
}
