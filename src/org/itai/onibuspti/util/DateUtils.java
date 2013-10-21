package org.itai.onibuspti.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtils {
	
	/**
	 * Converte um horário no formato "HH:mm:ss" para "HH:mm".
	 * @param time
	 * @return
	 */
	public static String formatTime(String time) {
		String result = time;
		if (time.length() == 8) { // 00:00:00
			
			int ini = (result.charAt(0) == '0' && result.charAt(1) != '0') ? 1 : 0;
			
			result = time.substring(ini, 5);
		}
		return result;
	}
	
	
	public static String formatDate(Date date) {
		DateFormat dateFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
		return dateFormat.format(date);
	}

}