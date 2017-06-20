package com.souvi.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.apache.commons.lang.time.FastDateFormat;

public class DateUtil {
	public static final String TIMEZONE = "Asia/Shanghai" ;
	/**
	 * <b>方法描述</b>： 将日期对象按照某种格式进行转换，返回转换后的字符串
	 * @param date	日期对象
	 * @param pattern	转换格式 例：yyyy-MM-dd
	 */
	public static String DateToString(Date date, String pattern) {
		String strDateTime = null;
		FastDateFormat fdf = FastDateFormat.getInstance(pattern);
		strDateTime = date == null ? null : fdf.format(date);
		return strDateTime;
	}
	/**
	 * 得到当前的系统时间
	 * yyyy-MM-dd HH:mm:ss
	 */
	public static String getSystemDate(){
		FastDateFormat fdf = FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss",TimeZone.getTimeZone(TIMEZONE));
		String currentTime = fdf.format(new Date());
		return currentTime;
	}
	/**
	 * 得到当前的系统时间
	 * yyyy-MM-dd HH:mm:ss sss
	 */
	public static String getSystemDateSSS(){
		FastDateFormat fdf = FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss sss",TimeZone.getTimeZone(TIMEZONE));
		String currentTime = fdf.format(new Date());
		return currentTime;
	}
	/**
	 * 得到当前的系统时间
	 * yyyy-MM-dd
	 */
	public static String getSystemDateTwo(){
		FastDateFormat fdf = FastDateFormat.getInstance("yyyy-MM-dd",TimeZone.getTimeZone(TIMEZONE));
		String currentTime = fdf.format(new Date());
		return currentTime;
	}
	/**
	 * 得到当前的系统时间
	 * yyyyMMddXXX
	 */
	public static String getSystemDateCountTwo(){
		FastDateFormat fdf = FastDateFormat.getInstance("yyyyMMdd",TimeZone.getTimeZone(TIMEZONE));
		String currentTime = fdf.format(new Date());
		//得到三位的随机数
		String[] str = {"1","2","3","4","5","6","7","8","9","0","A","B","C","D","E","F","G","H","I","J","K",
				"L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
		for(int i=0; i<3; i++){
			int rom = (int)(Math.random()*35);
			currentTime = currentTime + str[rom];
		}
		return currentTime;
	}
	/**
	 * 得到时间
	 * format：yyyy-MM-dd HH:mm:ss
	 */
	public static String getDate(Date date){
		FastDateFormat fdf = FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss");
		String currentTime = fdf.format(date);
		return currentTime;
	}
	/**
	 * 得到当前日期一个月之前的日期
	 * 格式化日期
	 * Format:hh:mm:ss
	 * @throws ParseException 
	 */
	public static String getDateBeforeOneMonth(){
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		sdf.setLenient(false);
		String calMonth="";
		calendar.setTime(new Date());
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int minute = calendar.get(Calendar.MINUTE);
		int second = calendar.get(Calendar.SECOND);
		String strmonth = String.valueOf(month);
		String strday = String.valueOf(day);
		if(month<10){
			strmonth = "0"+strmonth;
		}
		if(day<10){
			strday = "0"+strday;
		}
		
		calMonth = year+"-"+strmonth+"-"+strday+" ";
		String strhour = String.valueOf(hour);
		String strminute = String.valueOf(minute);
		String strsecond = String.valueOf(second);
		if(hour<10){
			strhour = "0"+strhour;
		}
		if(minute<10){
			strminute = "0"+strminute;
		}
		if(second<10){
			strsecond = "0"+strsecond;
		}
		
		calMonth += strhour+":"+strminute+":"+strsecond;
		
		
	
	return calMonth;
	}
	/**
	 * 得到系统日期的年月
	 * Format:yyyyMM
	 * @throws ParseException 
	 */
	public static String getYearMonthDate() throws ParseException{
		String date="";
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;// 这里月要加1
		if(0<month && 10>month){
			date = String.valueOf(year)+"0"+String.valueOf(month);
		}else{
			date = String.valueOf(year)+String.valueOf(month);
		}
		
		return date;
	}
	/**
	 * 得到系统日期的年月日
	 * Format:yyyyMMdd
	 * @throws ParseException 
	 */
	public static String getYearMonthDay() throws ParseException{
		String date="";
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH)+1;// 这里月要加1
		int day = calendar.get(Calendar.DATE);
		String strmonth = String.valueOf(month);
		String strday = String.valueOf(day);
		if(month<10){
			strmonth = "0"+strmonth;
		}
		if(day<10){
			strday = "0"+strday;
		}
		date = String.valueOf(year)+strmonth+strday;
		return date;
	}
	/**
	 * 得到系统日期的年月日
	 * Format:yyyyMMddHHmmss
	 * @throws ParseException 
	 */
	public static String getTimestamp() throws ParseException{
		String date="";
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH)+1;// 这里月要加1
		int day = calendar.get(Calendar.DATE);
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int minute = calendar.get(Calendar.MINUTE);
		int second = calendar.get(Calendar.SECOND);
		
		String strmonth = String.valueOf(month);
		String strday = String.valueOf(day);
		if(month<10){
			strmonth = "0"+strmonth;
		}
		if(day<10){
			strday = "0"+strday;
		}
		
		String strhour = String.valueOf(hour);
		String strminute = String.valueOf(minute);
		String strsecond = String.valueOf(second);
		if(hour<10){
			strhour = "0"+strhour;
		}
		if(minute<10){
			strminute = "0"+strminute;
		}
		if(second<10){
			strsecond = "0"+strsecond;
		}
		
		date = String.valueOf(year)+strmonth+strday+strhour+strminute+strsecond;
		return date;
	}
	/**
	 * 格式化日期
	 * Format:2012-11-11
	 * @throws ParseException 
	 */
	public static String getDateStringTwoString(String sDate){
			Calendar calendar = Calendar.getInstance();
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			String calMonth="";
			try {
				calendar.setTime(sdf.parse(sDate));
				int year = calendar.get(Calendar.YEAR);
				int month = calendar.get(Calendar.MONTH) + 1;// 这里月要加1
				int day = calendar.get(Calendar.DAY_OF_MONTH);
				String strmonth = String.valueOf(month);
				String strday = String.valueOf(day);
				if(month<10){
					strmonth = "0"+strmonth;
				}
				if(day<10){
					strday = "0"+strday;
				}
				
				calMonth = year+"-"+strmonth+"-"+strday;
			} catch (ParseException e) {
				return null;
			}
			
		
		return calMonth;
	}
	/**
	 * 格式化日期
	 * Format:hh:mm:ss
	 * @throws ParseException 
	 */
	public static String getTimeStringTwoString(String sDate){
			Calendar calendar = Calendar.getInstance();
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String calMonth="";
			try {
				calendar.setTime(sdf.parse(sDate));
				int hour = calendar.get(Calendar.HOUR_OF_DAY);
				int minute = calendar.get(Calendar.MINUTE);
				int second = calendar.get(Calendar.SECOND);
				String strhour = String.valueOf(hour);
				String strminute = String.valueOf(minute);
				String strsecond = String.valueOf(second);
				if(hour<10){
					strhour = "0"+strhour;
				}
				if(minute<10){
					strminute = "0"+strminute;
				}
				if(second<10){
					strsecond = "0"+strsecond;
				}
				
				calMonth = strhour+":"+strminute+":"+strsecond;
			} catch (ParseException e) {
				return null;
			}
			
			
		
		return calMonth;
	}
	/**
	 * 格式化日期
	 * Format:yyyy-MM-dd hh:mm:ss
	 * @throws ParseException 
	 */
	public static String getDateTimeStringTwoString(String sDate){
			if(null==sDate ||"".equalsIgnoreCase(sDate)){
				return "";
			}
			Calendar calendar = Calendar.getInstance();
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			sdf.setLenient(false);
			String calMonth="";
			try {
				calendar.setTime(sdf.parse(sDate));
				int year = calendar.get(Calendar.YEAR);
				int month = calendar.get(Calendar.MONTH) + 1;// 这里月要加1
				int day = calendar.get(Calendar.DAY_OF_MONTH);
				int hour = calendar.get(Calendar.HOUR_OF_DAY);
				int minute = calendar.get(Calendar.MINUTE);
				int second = calendar.get(Calendar.SECOND);
				String strmonth = String.valueOf(month);
				String strday = String.valueOf(day);
				if(month<10){
					strmonth = "0"+strmonth;
				}
				if(day<10){
					strday = "0"+strday;
				}
				
				calMonth = year+"-"+strmonth+"-"+strday+" ";
				String strhour = String.valueOf(hour);
				String strminute = String.valueOf(minute);
				String strsecond = String.valueOf(second);
				if(hour<10){
					strhour = "0"+strhour;
				}
				if(minute<10){
					strminute = "0"+strminute;
				}
				if(second<10){
					strsecond = "0"+strsecond;
				}
				
				calMonth += strhour+":"+strminute+":"+strsecond;
			} catch (ParseException e) {
				return null;
			}
			
			
		
		return calMonth;
	}
	/**
	 * 得到指定日期的之前一年之内的日期
	 * Format:2012-11
	 * @throws ParseException 
	 */
	public static List getBeforeYearTwoDate(String sDate) throws ParseException{
		List list = new ArrayList();
		for(int i=-11;i<=0;i++){
			Calendar calendar = Calendar.getInstance();
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			calendar.setTime(sdf.parse(sDate));
			calendar.add(Calendar.MONTH, i); // 得到某一个月
			int year = calendar.get(Calendar.YEAR);
			int month = calendar.get(Calendar.MONTH) + 1;// 这里月要加1
			String calMonth="";
			if(month>0 && month<10){
				calMonth = "0"+month;
			}else{
				calMonth = ""+month;
			}
			list.add(year +"-"+ calMonth);
		}
		
		return list;
	}
	/**
	 * 得到12个月之前的日期
	 * @throws ParseException 
	 */
	public static String getOneYearDate(String sDate) throws ParseException{
		String dateStr = "";
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM");
		calendar.setTime(sdf.parse(sDate));
		calendar.add(Calendar.MONTH, -11); // 得到某一个月
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;// 这里月要加1
		dateStr = year+"-"+month;
		
		return dateStr;
	}
	/**
	 * 比较两个日期的大小
	 */
	public static boolean compareTwoDate(String date1,String date2){
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			boolean flag = true;
			try {
				Date dateOne = sdf.parse(date1);
				Date dateTwo = sdf.parse(date2);
				//dateOne小于dateTwo时返回true,dateOne大于等于dateTwo返回false;
				flag = dateOne.before(dateTwo);
			} catch (ParseException e) {
				return false;
			}
		return flag;
	}
	/**
	 * 加上指定小时后比较两个日期的大小
	 * 
	 * @throws ParseException 
	 * @throws ParseException 
	 */
	public static boolean compareTwoDate2(String date1,String date2,int i){
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			boolean flag = true;
 			Calendar calendar1 = Calendar.getInstance();
			Calendar calendar2 = Calendar.getInstance();
			try {
				calendar1.setTime(sdf.parse(date1));
				calendar2.setTime(sdf.parse(date2));
				calendar1.add(Calendar.HOUR_OF_DAY, i); 
				flag = calendar1.before(calendar2);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
		return flag;
	}
	public static Date StringToDate(String dateStr,String formatStr){		
		SimpleDateFormat dateFormat = new SimpleDateFormat(formatStr);		
		Date date=null;		
		try {			
			date = dateFormat.parse(dateStr);		
		} catch (ParseException e) {			
			e.printStackTrace();		
		}		
		return date;	
	}
	/**
	 * 格式化日期
	 * Format:yyyy-MM-dd hh:mm:ss
	 * @throws ParseException 
	 */
	public static String[] getDateStringByNumber(String sDate,int count){
		if(null==sDate ||"".equalsIgnoreCase(sDate)){
			return null;
		}
		String dateArray[] = new String[count];
		int ia =0;
		for(int i=0;i<count;i++){
			Calendar calendar = Calendar.getInstance();
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
			sdf.setLenient(false);
			String calMonth="";
			try {
				calendar.setTime(sdf.parse(sDate));
				calendar.add(Calendar.SECOND, ia*2);     
				
				int year = calendar.get(Calendar.YEAR);
				int month = calendar.get(Calendar.MONTH) + 1;// 这里月要加1
				int day = calendar.get(Calendar.DAY_OF_MONTH);
				int hour = calendar.get(Calendar.HOUR_OF_DAY);
				int minute = calendar.get(Calendar.MINUTE);
				int second = calendar.get(Calendar.SECOND);
				String strmonth = String.valueOf(month);
				String strday = String.valueOf(day);
				if(month<10){
					strmonth = "0"+strmonth;
				}
				if(day<10){
					strday = "0"+strday;
				}
				
				calMonth = year+"-"+strmonth+"-"+strday+" ";
				String strhour = String.valueOf(hour);
				String strminute = String.valueOf(minute);
				String strsecond = String.valueOf(second);
				if(hour<10){
					strhour = "0"+strhour;
				}
				if(minute<10){
					strminute = "0"+strminute;
				}
				if(second<10){
					strsecond = "0"+strsecond;
				}
				
				calMonth += strhour+":"+strminute+":"+strsecond;
				
			} catch (ParseException e) {
				calMonth = getSystemDate();
				sDate = getSystemDate();
			}
			dateArray[i]=calMonth;
			ia++;
		}
			
		
		return dateArray;
	}
	/**
	 * 格式化日期
	 * Format:yyyyMMdd
	 * @throws ParseException 
	 */
	public static String getTimeStampbyStrDate(String sDate) throws ParseException{
		String date="";
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		calendar.setTime(sdf.parse(sDate));
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH)+1;// 这里月要加1
		int day = calendar.get(Calendar.DATE);
		String strmonth = String.valueOf(month);
		String strday = String.valueOf(day);
		if(month<10){
			strmonth = "0"+strmonth;
		}
		if(day<10){
			strday = "0"+strday;
		}
		date = String.valueOf(year)+strmonth+strday;
		return date;
	}
	
	public static Date addMonth(Date date,int month){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, month);
		return calendar.getTime();	
	}
	
	public static void main(String[] args){
		
		try {
			System.out.println(getSystemDate());
			
//			System.out.println(getTimestamp());
			Date dd = new Date();
			FastDateFormat fdf = FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss",TimeZone.getTimeZone(TIMEZONE));
			String currentTime = fdf.format(new Date());
			System.out.println(currentTime);
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			sdf.setTimeZone(TimeZone.getTimeZone(TIMEZONE));
			String currentTime2 = sdf.format(new Date());
			System.out.println(currentTime2);
			if(currentTime.equalsIgnoreCase(currentTime2)){
				System.out.println(true);
			}else{
				System.out.println(false);
			}
			char[] c = currentTime.toCharArray();
			char[] c2= currentTime2.toCharArray();
			for(int i=0;i<c.length;i++){
				System.out.print(c[i]);
				if(c[i]==c2[i]){
					System.out.println(true);
				}else{
					System.out.println(false);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		try {
//			String date = getDateStringTwoString("2014-1-1 01:02:03");
//			System.out.println(date);
//			//getOneYearDate("2013-07");
////			getBeforeYearTwoDate("2013-07-01");
//			
//			String day =getTimeStringTwoString("2014-01-01 01:02:03");
//			System.out.print("day:"+day);
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
}
