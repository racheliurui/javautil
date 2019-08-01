/**
 * 
 */
package javautil.time;

import java.io.BufferedReader;
import java.io.FileReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * @author RachelRuiLiu
 *
 */
public class TimeParser {

	public static Date parseUTCTime(String UTCTime) throws ParseException {

		DateFormat m_ISO8601Local = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
		m_ISO8601Local.setTimeZone(TimeZone.getTimeZone("UTC"));
		Date date = m_ISO8601Local.parse(UTCTime);

		return date;
	}

	public static Date addMinUTCTime(Date utcDate, int min) throws ParseException {

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(utcDate);
		calendar.add(Calendar.MINUTE, min);

		return calendar.getTime();
	}

	public static String getUTCStringByAddmingMin(String UTCTime, int min) throws ParseException {

		Date date = addMinUTCTime(parseUTCTime(UTCTime), min);
		DateFormat m_ISO8601Local = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
		m_ISO8601Local.setTimeZone(TimeZone.getTimeZone("UTC"));
		String sDate = m_ISO8601Local.format(date);

		return sDate;
	}

}
