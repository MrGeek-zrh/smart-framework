package com.smartframework.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 编码与解码工具类
* <p>Title: CodeUtil.java<／p>
* <p>Description: <／p>
* <p>Copyright: Copyright (c) 2020<／p>
* <p>Company: CUIT<／p>
* @author MrGeek
* @date 2020-09-22_23:40:46
* @version 1.0
 */
public final class CodeUtil {

	private static final Logger LOGGER = LoggerFactory.getLogger(CodeUtil.class);
	
	/**
	 * 将URL使用指定的编码机制将字符串转换为 
	 * 		application/x-www-form-urlencoded 格式。
	* <p>Title: encodeURL<／p>
	* <p>Description: <／p>
	* @param source
	* @return
	 */
	public static String encodeURL(String source) {
		String target;
		try {
			target = URLEncoder.encode(source, "UTF-8");
		} catch (Exception e) {
			LOGGER.error("encode url failure",e);
			throw new RuntimeException(e);
		}
		return target;
	}
	
	/**
	 * 将URL使用指定编码进行解码
	* <p>Title: decodeURL<／p>
	* <p>Description: <／p>
	* @param source
	* @return
	 */
	public static String decodeURL(String source) {
		String target;
		try {
			target = URLDecoder.decode(source,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			LOGGER.error("decode url failure",e);
			throw new RuntimeException(e);
		}
		return target;
	}
}
