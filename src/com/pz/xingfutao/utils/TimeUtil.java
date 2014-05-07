package com.pz.xingfutao.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class TimeUtil {

	public static String getFormattedTime(String format, long timeInSeconds){
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(timeInSeconds * 1000L);
		
		SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.CHINA);
		
		return dateFormat.format(calendar.getTime());
	}
}
