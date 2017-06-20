package com.souvi.common;

import java.util.Calendar;


/**
 * 获取主键
 *
 */
public class KeyUtil {
	
	/**
	 * 获取主键
	 * @return
	 */
	public static String getGuidID(){
		String uid = UUID.randomUUID().toString();
		return uid.replaceAll("-", "");
	}
	public static String getCustomerNoID(){
		Calendar date= Calendar.getInstance();
		String year = String.valueOf(date.get(Calendar.YEAR));
		String kzid = "";
		String[] str = {"1","2","3","4","5","6","7","8","9","0","A","B","C","D","E","F","G","H","I","J","K",
				"L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
		for(int i=0; i<6; i++){
			int rom = (int)(Math.random()*35);
			kzid = kzid + str[rom];
		}
		return year+kzid;
	}
	public static void main(String[] args) throws Exception {

	}
}
