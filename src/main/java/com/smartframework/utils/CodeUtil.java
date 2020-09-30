package com.smartframework.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 编码与解码工具类:
 * 		编码与解码的主要目的为了方便url中数据的传输
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
	 * 对向前台传递的数据进行编码：
	 * 		按照 application/x-www-form-urlencoded 的格式
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
	 *对前台传来的数据进行解码：
	 *		对 application/x-www-form-urlencoded 格式的字符串进行解码
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
