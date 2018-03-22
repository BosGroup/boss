package com.imooc.bos.bosUtils;

import org.springframework.util.DigestUtils;

public class Md5Util {
	
	/**
	 * 对密码进行MD5加密,循环加密10次
	 * MD5加密后不可逆,若要判断两次密码是否相同,可以比较用相同方式加密后的字符串是否相同判断
	 * @param pwd
	 * @return
	 */
	public static String encodePwd(String pwd){
		
		for(int i = 0;i < 10; i++){
			pwd = DigestUtils.md5DigestAsHex(pwd.getBytes());
		}
		return pwd;
	}
}
