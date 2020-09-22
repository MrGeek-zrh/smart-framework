package com.smartframework.utils;

/**
 * 数据类型转换工具类：
 * 		主要用于各种数据类型之间的转换
* <p>Title: CastUtil.java<／p>
* <p>Description: <／p>
* <p>Copyright: Copyright (c) 2020<／p>
* <p>Company: CUIT<／p>
* @author MrGeek
* @date 2020-09-22_00:30:47
* @version 1.0
 */
public final class CastUtil {

	/**
	 * 转换为String 类型
	* <p>Title: castToString<／p>
	* <p>Description: <／p>
	* @param obj
	* @return String形式的数据
	 */
	public static String castToString(Object obj) {
		return String.valueOf(obj);
	}
	
	/**
	 * 将数据转换为double型,如果数据无法转换为double型，则使用默认值0
	* <p>Title: CastToDouble<／p>
	* <p>Description: <／p>
	* @param obj
	* @return double型数据
	 */
	public static double CastToDouble(Object obj) {
		String  tmpValue="";
		try {
			tmpValue = String.valueOf(obj);
		} catch (NumberFormatException e) {
			tmpValue = "0";
		}
		return Double.parseDouble(tmpValue);
	}
	
	/**
	 * 将数据转换为long型,如果数据无法转换为long型，则使用默认值0
	* <p>Title: CastToLong<／p>
	* <p>Description: <／p>
	* @param obj
	* @return long型数据
	 */
	public static long CastToLong(Object obj) {
		String  tmpValue="";
		try {
			tmpValue = String.valueOf(obj);
		} catch (NumberFormatException e) {
			tmpValue = "0";
		}
		return Long.parseLong(tmpValue);
	}
	
	/**
	 * 将数据转换为int型,如果数据无法转换为int型，则使用默认值0
	* <p>Title: CastToInt<／p>
	* <p>Description: <／p>
	* @param obj
	* @return int型数据
	 */
	public static int CastToInt(Object obj) {
		String  tmpValue="";
		try {
			tmpValue = String.valueOf(obj);
		} catch (NumberFormatException e) {
			tmpValue = "0";
		}
		return Integer.parseInt(tmpValue);
	}
	
}
