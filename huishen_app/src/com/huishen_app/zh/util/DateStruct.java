package com.huishen_app.zh.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DateStruct {
	/* 默认的格式时间 */
	public static String defaultdatestruct;
	/* 识别的格式时间 */
	public static List<String> datestruct;
	/* 是否已经初始化 */
	public static boolean init = false;
	/* 默认和识别的时间格式 */
	private static SimpleDateFormat recongise;
	private static DateFormat defaults;

	// 初始化所有参数
	private static void init() {
		// 默认时间格式
		defaultdatestruct = "yyyy-MM-dd";
		// 识别时间格式
		datestruct = new ArrayList<String>();
		datestruct.add("yyyy-M-d");
		datestruct.add("yyyy-MM-dd");
		datestruct.add("yyyy:M:d");
		datestruct.add("yyyy:MM:dd");
		datestruct.add("yyyy年MM月dd日");
		datestruct.add("yyyy年M月d日");
		datestruct.add("yyyy/MM/dd");
		datestruct.add("yyyy/M/d");

		defaults = new SimpleDateFormat(defaultdatestruct);

		// 已经初始化
		init = true;
	}

	/**
	 * 在识别的时间格式上 比较两个时间差 -1:表示小于 0:表示等于 1:表示大于 2 表示异常 begin:在end前面是-1 ；在后面是1
	 * 
	 * @param timebegin
	 *            开始时间
	 * @param timeend
	 *            结束时间
	 * @return -1,1,1,2
	 */
	public static int comp_time(String timebegin, String timeend) {
		int result = 2;
		if (init == false)
			init();
		try {
			String tb, te;
			tb = recon_date(timebegin);
			te = recon_date(timeend);
			// 异常 不识别
			if (tb == null || te == null)
				return 2;
			// 结果标准化
			result = tb.compareToIgnoreCase(te);
			result = result < 0 ? -1 : result;
			result = result > 0 ? 1 : result;
		} catch (Exception e) {
			return 2;
		}
		return result;
	}

	/**
	 * 识别一个时间，返回默认格式的时间 如果识别不成功 返回null
	 * 
	 * @param datestr
	 * @return
	 */
	public static String recon_date(String datestr) {
		String default_date = null;
		if (init == false)
			init();
		// 依次识别时间格式
		Date date = null;
		for (String temp : datestruct) {
			try {
				// 识别一个指定的时间
				recongise = new SimpleDateFormat(temp);
				date = recongise.parse(datestr);
				default_date = defaults.format(date);
			} catch (Exception e) {
			}
		}
		return default_date;
	}

	/**
	 * 识别一个时间，返回默认格式的时间 如果识别不成功 返回null
	 * 
	 * @param datestr
	 * @return
	 */
	public static String recon_date(Date d) {
		String default_date = null;
		if (init == false)
			init();
		// 依次识别时间格式
		try {
			// 识别一个指定的时间
			default_date = defaults.format(d);
		} catch (Exception e) {
		}
		return default_date;
	}

}
