package com.huishen_app.zh.calendar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import android.util.Log;

/**
 * 用于日历显示的工具类
 * @author zhanghuan
 *
 */
public class CalendarUtil {
    public static int MAX_YEAR = 2100 ;
    public static int MIN_YEAR = 1970 ;
    public static String DATE_FORMAT = "yyyy-MM-dd" ;
	public CalendarUtil() {
	}
	/**
	 * 获得要显示的所有的月数
	 * @return
	 */
	public static int getAllMonth(){
		int year = MAX_YEAR - MIN_YEAR +1 ;
		
		return year*12 ;
	}
    
	public static Date getDate(int index){
		int year = MIN_YEAR+index/12;
		int month = index%12;
		Calendar date = new GregorianCalendar(year, month,1);
		Date d=date.getTime(); 
		return d;
	}
	
	public static Date getDate(String dateStr){
		if(dateStr == null || dateStr.equals("")){
			return null ;
		}
		SimpleDateFormat sf = new SimpleDateFormat(DATE_FORMAT);
		Date date = null;
		try {
			date = sf.parse(dateStr);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date;
	}
	public static Date getDate(int mouths,int day){
		int year = MIN_YEAR+mouths/12;
		int month = mouths%12;
		Calendar date = new GregorianCalendar(year, month,day);
		Date d=date.getTime(); 
		return d;
	}
	
	
	/**
	 * 获得月份的天数
	 * @param index fragment所在月的下标
	 * @return
	 */
	public static int getDayNum(int index){
		
		int year = MIN_YEAR+index/12;
		int month = index%12;
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
		Calendar date = new GregorianCalendar(year, month,1);
		int daynum = date.getActualMaximum(Calendar.DAY_OF_MONTH);
		SimpleDateFormat df1=new SimpleDateFormat("EEEE");
		Date d=date.getTime(); 
		Log.i("Main", df.format(d));
			 int day_of_week = date.get(Calendar.DAY_OF_WEEK); 
			 Date dt = date.getTime() ;
			 Log.i("Main",df.format(dt)+" "+df1.format(dt)+" DAY_OF_MONTH："+day_of_week+" date.getday:"+dt.getDay());
		return daynum ;
	}
	
	public static int getDayWeek(int index){
		int year = MIN_YEAR+index/12;
		int month = index%12;
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
		Calendar date = new GregorianCalendar(year, month,1);
		int dayweek = date.get(Calendar.DAY_OF_WEEK);
		return dayweek ;
		
	}
	
	/**
	 * 获取目前月份所在日历页面的下标
	 * @return
	 */
	public static int getCurrentMouth(){
		Calendar cal=Calendar.getInstance();
    	int year = cal.get(Calendar.YEAR) ;
    	int mouth = cal.get(Calendar.MONTH) ;
    	int day = cal.get(Calendar.DATE) ;
    	int currentIndex = (year - 1970)*12+mouth;
		return currentIndex ;
	} 
	
	/**
	 * 获得今天所在月份的第几天
	 * @return
	 */
	public static int getCurrentDay(){
		Calendar cal=Calendar.getInstance();
		int day = cal.get(Calendar.DATE) ;
		return day ;
	}
	/**
	 * 获得明天
	 * @return 格式为yyyy-MM-dd的字符
	 */
	public static String getTomorrow(){
		Calendar cal=Calendar.getInstance();
		cal.add(Calendar.DATE, 1); 
		Date date = cal.getTime() ;
		SimpleDateFormat sf = new SimpleDateFormat(DATE_FORMAT);
		return sf.format(date) ;
	}
	/**
	 * 获得从所给日期到MIN_YEAR中间的月份
	 * @param date
	 * @return
	 */
	public static int getMouthsForDate(Date date){
		Calendar cal=Calendar.getInstance();
		cal.setTime(date);
		int year = cal.get(Calendar.YEAR) ;
		int month = cal.get(Calendar.MONTH) ;
		int mouths = (year-1970)*12+month ;
		return mouths ;
	}
	
	/**
	 * 获得从所给日期到MIN_YEAR中间的月份
	 * @param String 字符型日期格式 2015-01-21
	 * @return
	 */
	public static int getMouthsForDate(String dateStr){
		if(dateStr == null || dateStr.equals("") ){
			Log.i("CalendarUtil", "date is null") ;
			return -1 ;
		}
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date;
		int mouths = -1 ;
		try {
			date = format.parse(dateStr);
			Calendar cal=Calendar.getInstance();
			cal.setTime(date);
			int year = cal.get(Calendar.YEAR) ;
			int month = cal.get(Calendar.MONTH) ;
			mouths = (year-1970)*12+month ;
			Log.i("CalendarUtil", date.toString()) ;
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Log.i("CalendarUtil", mouths+"") ;
		return mouths ;
	}
	/**
	 * 获得从所给日期的日
	 * @param String 字符型日期格式 2015-01-21
	 * @return
	 */
	public static int getDayForDate(String dateStr){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date;
		int day = 0 ;
		try {
			date = format.parse(dateStr);
			Calendar cal=Calendar.getInstance();
			cal.setTime(date);
			day = cal.get(Calendar.DAY_OF_MONTH) ;
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return day ;
	}
	/**
	 * 获得从所给日期的日
	 * @param Date 
	 * @return
	 */
	public static int getDayForDate(Date date){
		int day = 0 ;
			Calendar cal=Calendar.getInstance();
			cal.setTime(date);
			day = cal.get(Calendar.DAY_OF_MONTH) ;
			
		return day ;
	}
	
}
