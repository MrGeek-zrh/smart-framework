package com.smartframework.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 流操作工具类
* <p>Title: StreamUtil.java<／p>
* <p>Description: <／p>
* <p>Copyright: Copyright (c) 2020<／p>
* <p>Company: CUIT<／p>
* @author MrGeek
* @date 2020-09-22_23:30:13
* @version 1.0
 */
public final class StreamUtil {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(StreamUtil.class);

	/**
	 * 从流中读取数据，并将数据拼接成String 返回
	* <p>Title: getString<／p>
	* <p>Description: <／p>
	* @param is 输入流
	* @return
	 */
	public static String getString(InputStream is) {
		StringBuilder sb = new StringBuilder();
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		String readLine ;
		try {
			while ((readLine=br.readLine())!=null) {
				sb.append(readLine);
			}
		} catch (IOException e) {
			LOGGER.error("get string from stream failure",e);
			throw new RuntimeException(e);
		}
		return sb.toString();
	}
}
