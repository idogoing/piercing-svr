package com.piercing.qrcode.tool.utils;

import org.springframework.util.StringUtils;

import com.piercing.qrcode.constant.QrcodeConstant;
import com.piercing.qrcode.o.bo.Qrcode;

public class QrcodeUtils {
	
	/**
	 * 分割券的前缀
	 * @param code
	 * @return
	 */
	public static String cutPrefix(String code){
		if(StringUtils.isEmpty(code)){
			return "";
		}
		int separatorIndex = code.indexOf(QrcodeConstant.CODE_SEPARATOR);
		if(separatorIndex == -1){
			return "";
		}
		String prefix = code.substring(0,separatorIndex);
		return prefix;
	}
	
	public static String realCodeValue(String code){
		if(StringUtils.isEmpty(code)){
			return "";
		}
		int separatorIndex = code.indexOf(QrcodeConstant.CODE_SEPARATOR);
		if(separatorIndex == -1){
			return code;
		}
		String value = code.substring(separatorIndex+1);
		return value;
	}
	
	/**
	 * 将一个二维码转换为Qrcode对象
	 * @param code
	 * @return
	 */
	public static Qrcode transCodeToQrcode(String code){
		if(StringUtils.isEmpty(code)){
			return new Qrcode();
		}
		int separatorIndex = code.indexOf(QrcodeConstant.CODE_SEPARATOR);
		Qrcode qrcode = new Qrcode();
		if(separatorIndex == -1){
			qrcode.setValue(code);
			return qrcode;
		}
		String prefix = code.substring(0,separatorIndex);
		String value = code.substring(separatorIndex+1);
		qrcode.setPrefix(prefix);
		qrcode.setValue(value);
		qrcode.setSeparator(QrcodeConstant.CODE_SEPARATOR);
		return qrcode;
	}
	
	public static void main(String[] args) {
		
		String code = "LONAKING:432879";
		String cutPrefix = cutPrefix(code);
		System.out.println(cutPrefix);
		
		String value = realCodeValue(code);
		System.out.println(value);
	}
}
