package com.example.bootdemo.utils.DataFormatUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public final class DateUtil {

	
	public static final Date convertString2DateInMiType(String strDate) throws ParseException {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sf.parse(strDate);
	}

	public static final Date convertString2DateInMiType1(String strDate) throws ParseException {
		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
		return sf.parse(strDate);
	}
	
	
	public static final Date getCurrentDateInMiType() throws ParseException {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date(System.currentTimeMillis());
		return sf.parse(sf.format(date));
	}
	

	/**
	 * 将Date转换成特定的字符串格式
	 * @param date
	 * @return
	 */
	public static String getTimeString(Date date) {
		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
		return sf.format(date);
	}

	/**
	 * 将Date转换成特定的字符串格式
	 * @param date
	 * @return
	 */
	public static String getTimeString1(Date date) {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sf.format(date);
	}


	/**
	 * 得到目标时间前days天
	 * @param targetTime
	 * @param days
	 * @return
	 */
	public static Date beforeDay(Date targetTime, int days){
		Calendar now = Calendar.getInstance();
		now.setTime(targetTime);
		now.set(Calendar.DATE, now.get(Calendar.DATE)-days);
		return now.getTime();
	}

	/**
	 * 得到目标时间后days天
	 * @param targetTime
	 * @param days
	 * @return
	 */
	public static Date afterDay(Date targetTime, int days){
		Calendar now = Calendar.getInstance();
		now.setTime(targetTime);
		now.set(Calendar.DATE, now.get(Calendar.DATE) + days);
		return now.getTime();
	}

	public static void main(String[] args) throws ParseException {
		System.out.println(afterDay(new Date(),1));
	}
}
